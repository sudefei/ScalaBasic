package sdf.spark

import java.net.URI
import java.sql.{Connection, PreparedStatement, Timestamp}

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, FileUtil, Path}
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, DataFrameReader, Row, SQLContext, SaveMode}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.phoenix.spark._
import org.slf4j.LoggerFactory
/**RDD逻辑上是分区的，每个分区的数据抽象存在，计算的时候通过一个函数计算每个分区的数据
  *分区的好处： 增加并行度，实现分布式计算，减少通讯开销
  *local(本地模式) 默认是本地机器CPU的数目
  *local[N] 设置为 N个
  * Mesos 模式，不设置的话，默认是8
  *
  * conf.setMaster(local[numPartition])
  * sc.parallelize(seq,numPartition)
  * sc.makeRDD(seq,numPartition)
  *
  * // repartition() 可以修改分区，并且有效
  * // coalesce() 能有效的折叠（coalesce） 同一个工作节点的分区，以便在重新分区时同一个WorkNode上避免再次shuffle,使用coalesce 可以将数据数据合并到一个分区，避免shuffle
  */
object PartitionRDD {
  // AppName: Application Name
  // Master: 设置部署模式
  // 本地模式(local) 默认是本地机器的CPU的数目 | Mesos 模式 默认是8个
  val conf=new SparkConf().setAppName("RddPartition").setMaster("local[8]")
  val sc=new SparkContext(conf)
  val list=List("hello","world","sdf","jack")

  def main(args: Array[String]): Unit = {

    parallelizePartition()
    makeRDDPartition()
  }

  def parallelizePartition(): Unit ={
    println("==================parallelize======================")
    val size_01 = sc.parallelize(list).partitions.size
    val size_02 = sc.parallelize(list,2).partitions.size
    val rdd = sc.parallelize(list,2).repartition(3)
    val size_03 = rdd.partitions.size
    val size_04 = sc.parallelize(list,2).repartition(3).partitions.size
    val rdd_02 = sc.parallelize(list,2).coalesce(4)
    val size_05 = rdd_02.partitions.size
    val rdd_03 = rdd_02.coalesce(5)
    val size_07 = rdd_03.partitions.size
    val size_06 = sc.parallelize(list,2).coalesce(4).partitions.size
    // 8 2 3 3 2 2 2
    List(size_01,size_02,size_03,size_04,size_05,size_06,size_07).foreach(print)
    println("=======================End==========================")

  }

  def makeRDDPartition(): Unit ={
    println("======================makeRDD========================")
    val size_01 = sc.makeRDD(list).partitions.size                                   //8
    val size_02 = sc.makeRDD(list,2).partitions.size                     //2
    val rdd = sc.makeRDD(list,2).repartition(3)
    val size_03 = rdd.partitions.size                                                //3
    val size_04 = sc.makeRDD(list,2).repartition(3).partitions.size  //3
    val rdd_02 = sc.makeRDD(list,2).coalesce(4)
    val size_05 = rdd_02.partitions.size                                              //2
    val rdd_03 = rdd_02.coalesce(5)
    val size_07 = rdd_03.partitions.size                                              // 2
    val size_06 = sc.makeRDD(list,2).coalesce(4).partitions.size //2
    // 8 2 3 3 2 2 2
    List(size_01,size_02,size_03,size_04,size_05,size_06,size_07).foreach(print)
    println("=======================End==========================")

  }


}
