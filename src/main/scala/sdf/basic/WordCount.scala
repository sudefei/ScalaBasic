package sdf.basic

import java.io.File

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.io.Source

object WordCount {

  val conf=new SparkConf().setAppName("wordcount").setMaster("local[2]")
  val sc=new SparkContext(conf)

  def main(args: Array[String]): Unit = {

//     fileWordCount("D:\\File\\WorkFile\\Test_data\\word.txt")

     sparkWordCount("D:\\File\\WorkFile\\Test_data\\word.txt")

  }

  def fileWordCount(filepath:String): Unit ={
    // 对本地的文件进行处理
    val source=Source.fromFile(new File(filepath)); //scala.io.BufferedSource = { /* compiled code */ }
    val lines = source.getLines();//scala.collection.Iterator[scala.Predef.String]
    val wordList = lines.flatMap(_.split(" ")).toList;
    val groupWord = wordList.map((_,1)).groupBy(_._1)
    // Tuple(azkaban,List((azkaban,1)))
    //key:String ( mapValues) 使用函数对value 进行结果的处理 结果为C，最终返回结果为 Map（key,C）
    groupWord.mapValues(_.size).foreach(println)
    source.close()
  }

  def sparkWordCount( filepath:String ): Unit ={
    // 默认读取的是 Hdfs 上文件的路径
    // 对集群上的数据进行处理
    val lines =sc.textFile(filepath); //org.apache.spark.rdd.RDD[scala.Predef.String]
    val wordAll = lines.flatMap(_.split(" "))
    val wordcount = wordAll.map((_,1)).reduceByKey((a,b)=> a+b)
    wordcount.collect()
    wordcount.foreach(println)
  }



}
