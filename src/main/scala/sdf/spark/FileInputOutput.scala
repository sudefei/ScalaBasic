package sdf.spark

import org.apache.spark.{SparkConf, SparkContext}

import scala.util.parsing.json.JSON
import org.apache.spark.rdd.JdbcRDD
import org.apache.hadoop.fs.{FileSystem, FileUtil, Path}
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.io._
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql._
import java.io.File
import java.sql.DriverManager
import java.io.StringReader

//import au.com.bytecode.opencsv.CSVReader

//import org.apache.hadoop.hbase.mapreduce.TableInputFormat

import scala.io.Source


object FileInputOutput {

  val conf=new SparkConf().setAppName("RddPartition").setMaster("local")
  val sc=new SparkContext(conf)
  val sqlContext =new SQLContext(sc)
//  val spark = SparkSession.builder().getOrCreate();

  val hbaseConf = HBaseConfiguration.create()
  val zkUrl = hbaseConf.get("hbase.zookeeper.quorum") + ":" + hbaseConf.get("hbase.zookeeper.property.clientPort")

  val mysqlDriverUrl = "jdbc:mysql://192.168.160.219/hive"
  val user = "root"
  val password = "123456"
  val mysqlDriver = "com.mysql.jdbc.Driver"
  val dbtable="hive.VERSION"

  val oracleDriverUrl = s"jdbc:oracle:thin:@//10.10.185.153/BIGDATAPDB"
  val oracleDriver = "oracle.jdbc.driver.OracleDriver"

  val phoenixUrl=s"jdbc:phoenix:localhost:2181"
  val phoenixTable="PhoenixTest"
  val phoenixDriver="org.apache.phoenix.jdbc.PhoenixDriver"

  val prop={
    val p = new java.util.Properties()
    p.put("driver",oracleDriver)
    p.put("user",user)
    p.put("password",password)
  }

  def txtFile(inputPath: String,outputPath: String)={

    // 本地文件夹 path="file:///data/sparkdata/wordcount.txt"
    // 默认是 hdfs 上文件路径 path="/data/sparkdata/wordcount.txt"
    //textfile(path) 是输入路径
    val txtFileRDD = sc.textFile(inputPath);
    //saveAsTextFile(path) 是输出路径
    txtFileRDD.saveAsTextFile(outputPath);
  }

  def parquetFile(inputPath: String,outputPath: String)={
     val studentDF = sqlContext.read.parquet(inputPath)
     studentDF.write.parquet(outputPath)

    val peopleDF = sqlContext.read.format("parquet").load(inputPath)
    peopleDF.write.format("csv").save(outputPath)
  }

  def csvFile_01(inputPath: String,outputPath: String)={
    // 1. spark-csv 读取CSV数据
    val wdf = sqlContext.read
      .format("com.databricks.spark.csv")
      .option("header", "false")
      .option("inferSchema", true.toString)
      .option("delimiter", "\001")
      .load(inputPath)
    wdf
  }

  def csvFile_02(inputPath: String,outputPath: String)={
    // 2. 使用 opencsv 读取 csv
    val fileRDD=sc.textFile(inputPath)
//    val csvRDD=fileRDD.map{ line => val reader=new CSVReader(new StringReader(line)); reader.readNext()}
//    csvRDD   //5
  }

  def csvFile_03(inputPath: String,outputPath: String)={
    // 3. 使用 SparkSession 直接读取 CSV 文件
    // val peopleDF=spark.read.csv(filepath)
    val peopleDF = sqlContext.read.format("csv").load(inputPath)
    peopleDF.write.format("json").save(outputPath)
    peopleDF.write.json(outputPath)
    peopleDF
  }

    def jsonFile(inputPath: String,outputPath: String)={
       //scala 自带scala.util.parsing.JSON 来解析json格式数据
      //调用 JSON.parseFull() 可以对json 数据进行解析
      val jsonString = sc.textFile(inputPath)  //每行json 数据
       // 解析成功返回Some，解析失败返回 None
       //对 jsonString RDD 中的每个元素进行遍历
       val result=jsonString.map( s => JSON.parseFull(s))
       result.foreach({ r => r match {
         case Some(map:Map[String,Any]) => println(r)
         case None =>  println("parsing failed !")
         case other => println("Unknown data structure" + other)
       }}
       )
  }

  /**
    * Spark 连接数据库有两种方式
    *  1.采用 jdbc 连接方式连接
    *   spark.read.format("jdbc").option("url",DriverUrl).option("driver",Driver).option("user",user).option("password",password).load()
    *  2.采用spark-phoenix 或者其他关联的 jar 直接连接
    *   spark.load("org.apache.phoenix.spark", Map("table" -> tableName, "zkUrl" -> zkUrl))
    */

  def MysqlInputOutput_01(sqlContext: SQLContext )={
    /**读取 mysql 数据库有多种连接方式：
      *  1.rdd.JDBCRDD 进行连接
      */
    val rdd=new JdbcRDD(sc,
        () => {Class.forName("com.mysql.jdbc.Driver").newInstance()
          DriverManager.getConnection("jdbc:mysql://192.168.160.219:3306/hive","root","123456")},
        "select * from rddtable where id >= ? and id <= ?;",1,10,1,
        r => (r.getInt(1),r.getString(2)
        ))

  }

  def MysqlInputOutput_02(sqlContext: SQLContext )={
   /**  2.老的版本是 SQLContext / HiveContext
      *  3.新的版本   SparkSession 内部封装了 SparkContext，最终实际是交给 SparkContext完成
      *  SparkCore (RDD)         用对象SparkContext对 RDD进行处理
        SparkSQL (DataFrame)    用SparkSession 对 DataFrame（相当于是二维表格，带有schema表结构）进行处理
    */
   //老的 SQLContext 处理
    // sqlContext、SparkSession.read.format("") 可以读取任意的数据格式
   val reader = getMysqlReader(sqlContext)
    reader.load()
  }

  def getMysqlReader(sQLContext: SQLContext): DataFrameReader = {
    sQLContext.read.format("jdbc").option("url", mysqlDriverUrl).option("driver", mysqlDriver)
      .option("dbtable", dbtable)
      .option("user", user)
      .option("password", password)
  }

  def getMysqlSchema(sQLContext: SQLContext): StructType = {
    getMysqlReader(sQLContext).option("dbtable", dbtable)
      .load().schema
  }

  def writeMysqlTable(dataFrame: DataFrame, tableName: String, saveMode: SaveMode): Unit = {
    dataFrame.write.format("jdbc").option("url", mysqlDriverUrl).option("driver", mysqlDriver)
      .option("dbtable", dbtable)
      .option("user", user)
      .option("password", password).mode(saveMode).save()
  }

  def getOracleReader(sQLContext: SQLContext): DataFrameReader = {
    sQLContext.read.format("jdbc").option("url", oracleDriverUrl).option("driver", oracleDriver)
      .option("dbtable", dbtable)
      .option("user", user)
      .option("password", password)
  }

  def getOracleSchema(sQLContext: SQLContext): StructType = {
    getOracleReader(sQLContext).option("dbtable", dbtable)
      .load().schema
  }

  def getPhoenixReader_01(sQLContext: SQLContext): DataFrameReader ={
    //Spark 不能直接访问 Hbase 的数据，只能通过访问 phoenix 访问 Hbase
    // 1.连接 JDBC ，直接连接 PhoneixDriver(org.apache.phoenix.jdbc.PhoenixDriver)
    sQLContext.read.format("jdbc").option("driver",phoenixDriver).option("url",phoenixUrl).option("dbtable",phoenixTable)
  }

  def getPhoenixReader_02(sQLContext: SQLContext): DataFrameReader ={
    // 2.使用 phoenix-spark 直接连接Phoenix集群 (org.apache.phoenix.spark)
    sQLContext.read
      .format("org.apache.phoenix.spark")
      .options(Map("table" -> dbtable, "zkUrl" -> zkUrl))
  }

  def writePhoenixTable(dataFrame: DataFrame, tableName: String, saveMode: SaveMode): Unit = {
    dataFrame.write.format("org.apache.phoenix.spark").mode(saveMode).option("table", tableName).option("zkUrl", zkUrl).save()
  }


}
