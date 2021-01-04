package sdf.sparkStreaming

import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.spark.{SparkConf, SparkContext}
//import org.apache.spark.streaming.kafka.KafkaUtils
//import org.apache.spark.streaming.Seconds
//import org.apache.spark.streaming.StreamingContext
import kafka.serializer.StringDecoder
import scala.collection.immutable.HashMap


/** Flume -> KafKa -> SparkStreaming
  *  1. 定义一个 对日志监控的 Flume  ,通过网络 avro 类型，传输到spark 服务器的666端口上
  *     $bin/flume-ng agent --conf conf/ --conf-file jobs/log-to-flume.conf --name a1              -Dflume.root.logger=INFO,console
  *
  *  2. 定义第二个 Flume,将数据由spark服务器的 666端口，传输到 kafka 集群
  *      $bin/flume-ng agent --conf conf/ --conf-file jobs/port-to-kafka.conf --name a2              -Dflume.root.logger=INFO,console
  *  3. 配置 Kafka,Kafka的配置信息与 port-to-kafka 信息保持一致
  *      $bin/kafka-topics --create --zookeeper hadoop-senior.shinelon.com:2181 --replication-factor 1 --partitions 1 --topic mytopic
  *
  *  4. 编写 spark Streaming 将 kafka 数据传输到 sparkStreaming
  */

object KafkaDataSpark {

//  Logger.getLogger("org.apache.spark").setLevel(Level.WARN);
//  Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.ERROR);
//
//  val conf = new SparkConf().setAppName("stocker").setMaster("local[2]")
//  val sc = new SparkContext(conf)
//
//  val brokers = "spark1:9092,spark2:9092,spark3:9092"
//
//  val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers, "serializer.class" -> "kafka.serializer.StringEncoder")
//
//  val ssc = new StreamingContext(sc, Seconds(5))
//
//  val topicMap = Map("mytopic" -> 1)
//
//  // read data
//  val lines = KafkaUtils.createStream(ssc, "hadoop-senior.shinelon.com:2181", "testWordCountGroup", topicMap).map(_._2)
//
//  val words = lines.flatMap(_.split(" "))
//  val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)
//  wordCounts.print()
//
//  ssc.start()
//  ssc.awaitTermination()

}
