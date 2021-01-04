package sdf.basic

//import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Author defei.su
  * @Date 2020/9/1 17:07
  */
object ScalaPractice {

  val conf = new SparkConf().setMaster("local[4]").setAppName("ETL")
  val  sc=new SparkContext(new SparkConf().setMaster("local[4]").setAppName("ETL"))

//  val spark = SparkSession.builder().enableHiveSupport().config(conf).getOrCreate()
  val list=List(1,2,3)


  def main(args: Array[String]): Unit = {

    list.map((_,2)).foreach(print)
  }


  def calculatePartition(): Unit ={
    /**  mutable ：可变的
      *  immutable: 不可变的
      */
    // List[Int] = List(2, 4, 6)
    list.map((_*2))
    //scala.collection.immutable.Seq[(Int, Int)] = List((1,2), (2,2), (3,2))
    list.map((_,2)).toSeq.foreach(println)
    val size = sc.parallelize(list).partitions.size
    val size_01 = sc.parallelize(list,2).partitions.size
    val size_02 = sc.parallelize(list,2).repartition(8).partitions.size
    val size_06 = sc.parallelize(list,2).coalesce(8).partitions.size

    val size_03 = sc.makeRDD(list).partitions.size
    val size_04 = sc.makeRDD(list,3).partitions.size
    val size_05 = sc.makeRDD(list,3).repartition(8).partitions.size
    val size_07 = sc.makeRDD(list,3).coalesce(8).partitions.size


    println(size)     //4
    println(size_01)  //2
    println(size_02)  //8
    println(size_03)  //4
    println(size_04)  //3
    println(size_05)  //8
    println(size_06)  //2
    println(size_07)  //3
  }

  /** map 可以有返回值
    * foreach 没有返回值 void
    */
  def mapForeachDiff(): Unit ={
    val rdd = sc.makeRDD(list)

    // def map[U](f : scala.Function1[T, U])(implicit evidence$3 : scala.reflect.ClassTag[U]) : org.apache.spark.rdd.RDD[U] = { /* compiled code */ }
    rdd.map(print)

    //  def foreach(f : scala.Function1[T, scala.Unit]) : scala.Unit = { /* compiled code */ }
    rdd.foreach(print)
  }

}
