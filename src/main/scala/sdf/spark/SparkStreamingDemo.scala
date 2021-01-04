package sdf.spark

//import org.apache.spark.streaming._
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}
//import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.rdd.RDD

import scala.collection.mutable

/** SparkCore  sc:SparkContext 处理 RDD
  * SparkSQL   spark:SQLContext、HiveContext、SparkSession 处理 DataFrame
  * SparkStreaming  ssc:SparkStreamingContext 处理实时数据
  */

object SparkStreamingDemo {
//
//  val sparkConf=new SparkConf().setAppName("wcStreaming").setMaster("local")
//  val sc=new SparkContext(sparkConf)
//  val ssc=new StreamingContext(sc,Seconds(1))
//
//  def wordCount(lines : DStream[scala.Predef.String]): Unit ={
//    val wordList = lines.flatMap(_.split(" "))
//    val wordcount= wordList.map((_,1)).reduceByKey(_+_)
//    wordcount.print()
//  }
//
//  def sscStart(): Unit ={
//    //开启 sparkStreaming (流计算)对文件进行监控
//    ssc.start()
//    //运行过程中发生错误的时候中断，不错的话一直运行
//    ssc.awaitTermination()
//  }
//  /**
//    * 对文件进行监控，一旦文件夹下文件个数或者文件内容发生改变的话，就会返回数据
//    *  返回的数据是经过 处理的数据 例如 wc 等
//   */
//  def fileStreaming(filepath:String): Unit ={
//    //  def textFileStream(directory : scala.Predef.String) : org.apache.spark.streaming.dstream.DStream[scala.Predef.String] = { /* compiled code */ }
//    val lines = ssc.textFileStream(filepath)
//    //对获取到的数据进行处理
//    wordCount(lines)
//    ssc.start()
//  }
//
//  /**  定义套接字流格式的作为输入端，监听该端口数据发生的改变
//    * @param args  传入的参数
//    */
//  def socketStreaming(args: Array[String]): Unit ={
//    //在终端开启一个服务端，启动一个 tcp 协议，hostname和port要一致
//     if (args.length<2){
//       System.err.println("Usage: NetworkWordCount <hostname> <port>")
//       System.exit(1)
//     }
//    //定义SocketStream ，出入 hostname,port
//    //def socketTextStream(hostname : scala.Predef.String, port : scala.Int, storageLevel : org.apache.spark.storage.StorageLevel = { /* compiled code */ }) : org.apache.spark.streaming.dstream.ReceiverInputDStream[scala.Predef.String] = { /* compiled code */ }
//    val lines=ssc.socketTextStream(args(0),args(1).toInt,StorageLevel.MEMORY_AND_DISK_SER)
////    val lines=ssc.socketTextStream("master.cdh.com",9999,StorageLevel.MEMORY_AND_DISK_SER)
//    wordCount(lines)
//    ssc.start()
//  }
//
//  def RDDStreaming(): Unit ={
//     //生成RDD队列
//    val rddQueue=new mutable.SynchronizedQueue[RDD[Int]]();
//    // 将RDD 队列挂接到 SparkStreaming 上，创建输入流（RDD 队列流的形式）
//    //def queueStream[T](queue : scala.collection.mutable.Queue[org.apache.spark.rdd.RDD[T]], oneAtATime : scala.Boolean = { /* compiled code */ })(implicit evidence$13 : scala.reflect.ClassTag[T]) : org.apache.spark.streaming.dstream.InputDStream[T] = { /* compiled code *
//    val queueStream = ssc.queueStream(rddQueue)  //SparkStreaming创建RDD队列流形式的输入流
//    //统计整数出现的次数
//    //def map[U](mapFunc : scala.Function1[T, U])(implicit evidence$2 : scala.reflect.ClassTag[U]) : org.apache.spark.streaming.dstream.DStream[U] = { /* compiled code */ }
//    val mappedStream = queueStream.map(r=>(r%10,1))
//    val reduceStream = mappedStream.reduceByKey(_+_)
//    reduceStream.print()
//    // 开启流计算监控
//    ssc.start()
//    for (i<-1 to 10){
////      每隔 1s 向RDD队列中添加一个RDD
//      rddQueue += ssc.sparkContext.makeRDD(1 to 100,2)
//      Thread.sleep(1000)
//    }
//    //手动关闭
//    ssc.stop()
//  }


}
