package sdf.kafka

import java.lang
import java.util.{Collections, Properties}

import org.apache.kafka.clients.consumer.{CommitFailedException, ConsumerRecord, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.{PartitionInfo, TopicPartition}
import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}
import org.apache.kafka.common.TopicPartition
import org.apache.spark.SparkConf
//import org.apache.spark.streaming.{Minutes, Seconds, StreamingContext}
import sdf.config.ProjectConf

import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer

/**
  * @Author defei.su
  * @Date 2020/9/1 16:10
  * Kafka 简介： kafka 数据存储 ,kafka 不会一直保留数据，也不会等到消费者将数据都消费之后才删除数据。
  *
  */
object KafkaAllUtils {


  val kafkaProperties: Properties = new Properties()
  //获取 broker
  kafkaProperties.put("bootstrap.servers", ProjectConf.prop.getProperty("bootstrap.servers.test"))
  //Key ,Value 的序列化
  kafkaProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  kafkaProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  kafkaProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  kafkaProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  kafkaProperties.put("group.id", "TEST01")

  val producer:KafkaProducer[String,String] = new KafkaProducer(kafkaProperties)

  val consumer = new KafkaConsumer[String, String](kafkaProperties)


  def main(args: Array[String]): Unit = {
    if (args.length < 4) {
      // broker_id , topic, 每秒发送多少条信息，每条消息包含多少个单词
      System.err.println("Usage:KafkaWordProducer<metadataBroker>,<topic>" + "<MessagePerSec>,<wordsPerMessage>")
      System.exit(1)
    }
    produceData(args)
    getEndOffset("787_MES")
  }

  def produceData(args: Array[String]): Unit ={
    //提取器： 将参数赋值给 数组中的值
    val Array(brokers, topic, messagePerSec, wordPerMessage) = args
    //Producer 不断的向 Kafka 发送信息
    while (true) {
      (1 to messagePerSec.toInt).foreach { messageNum => //每秒发多少条消息
        val str = (1 to wordPerMessage.toInt) //每条消息发多少个单词
          .map(x => scala.util.Random.nextInt(10).toString) // 遍历 1 的时候，在(1,10) 之间随机取数
          .mkString(" ") //将 5 次 取出来的随机数 拼接成 String
        print(str)
        println()
        val message = new ProducerRecord[String, String](topic, null, str)
        producer.send(message)                                //每秒钟发3 条消息，每条信息包含 5 个单词
      }
      Thread.sleep(1000)
    }
  }
  /** Kafka 发送消息的三种模式
    *       1.发送并忘记(fire-and-forget): 消息发送给服务器，并不关系心数据是否到达，不过kafka 具备高可用，会自动尝试重发，该方法可能会丢失一些数据。
    *
    *       2.同步发送(send): 使用send ,且会返回一个Future对象，通过 get 等待，即可知道数据是否发送成功。
    *       优点：每次发送都等待返回响应是否正常。
    *       缺点：同一时间只能有一个消息在发送，会造成很多消息无法直接发送，造成消息滞后，无法发挥最大效益
    *
    *       3.异步发送(send): 使用send,并指定一个回调函数，服务器在返回响应时调用该函数
    *       优点：同一时间可有多个消息同时发送，无需一个一个的等待返回响应，当服务器返回响应时会自动的调用编写的回调函数，对异常情况进行处理。
    *
    * 发送的消息会被发送至哪一个分区？
    *       1.发送消息时指定分区号。
    *       2.发送的消息是 Key:Value 格式的数据，则根据 Key的Hash函数 指定一个分区。
    *       3.未指定分区，也没有Key ,将会以循环的方式分配一个分区。
    */
  def sendToKafka(topic:String,kafkaKey:String,kafkaValue:String): Unit = {
    //public ProducerRecord(String topic, Integer partition, K key, V value)
    //    同步发送: 通过 send 发送消息，返回一个包含 RecordMetaData的 Future 对象
    //              在通过get 进行等待，就可知道消息是否发送成功。
    //public Future<RecordMetadata> send(ProducerRecord<K, V> record)
    val value = producer.send(new ProducerRecord(topic, kafkaKey, kafkaValue)).get()
    //    producer.close()

    // 异步发送: 通过 send 发送消息，指定一个回调函数，服务器在返回响应时调用该函数。
    producer.send(new ProducerRecord(topic, kafkaKey, kafkaValue), new SampleProducerCallBack())
  }

    //    producer.send(new  ProducerRecord(topic,kafkaKey,kafkaValue),new Callback {
    //      override def onCompletion(recordMetadata: RecordMetadata, e: Exception): Unit = {
    //        if (e != null){
    //          e.printStackTrace("Failed to send message with Exception :"+e)}}} )}

  def getEndOffset(topicName:String): lang.Long ={
        // 首先需要订阅topic
        // Collections.singletonList("733Mes")
        consumer.subscribe(Collections.singletonList(topicName))
        // asScala 隐士转换，将 java 类型转换为 scala
        val partitionInfos  = consumer.listTopics().get(topicName).asScala
        var array: ArrayBuffer[TopicPartition] =ArrayBuffer()
        // 获取该 topic 的所有分区集合
        partitionInfos.foreach(x => {
          val topicPartition: TopicPartition = new TopicPartition(topicName, x.partition())
          array += topicPartition
        })
        // public Map<TopicPartition, Long> endOffsets(Collection<TopicPartition> partitions) {
        val offsetMap =consumer.endOffsets(array.asJavaCollection).asScala
    //    val endOffset: lang.Long =offsetMap.values.toVector(0)
        val sumOffset: lang.Long = offsetMap.values.reduce(_+_)

        sumOffset
  }

    //  def produceWord(): Unit ={
    ////    Streaming.Example.setStreamingLlogLevels()   //设置日志格式，正确显示日志
    ////    val sc=new SparkConf().setAppName("KafkaWordCount").setMaster("local[2]")
    ////    val ssc=new StreamingContext(sc,Seconds(10))
    ////    //设置检查点，数据量大时，设置检查点，防止数据的丢失,可以是本地或者是 HDFS。检查点就是将数据保存在某个文件系统
    ////    ssc.checkpoint("file:///usr/local/data/kafka/checkpoint")
    ////    val zkQuorum="localhost:2181"   //zookeeper 服务器的地址
    ////    val group="1"  //topic 所在的group，可以设置为自己想要的名称，比如不用 1，而val group ="test-consumer-group"
    ////    val topics="wordsender"         //如果是多个，用 “，” 隔开
    ////    val numThreads=1                //每个topic的分区数
    ////    //多个 topic , 每个 topic 都是一个元组 （topicName,partitionNum）
    ////    val topicMap=topics.split(",").map((_,numThreads.toInt)).toMap
    ////    //1. Spark Streaming 数据源的创建
    ////    val lineMap = .createStream(ssc,zkQuorum,group,topicMap)
    ////    //2. 编写转换 ，由于发数据的时候是（null,str）格式
    ////    val lines=lineMap.map(_._2)
    ////    val words=lines.flatMap(_.split(" "))
    ////    val pair=words.map((_,1))
    ////    val wordCounts=pair.reduceByKeyAndWindows(_+_,_-_,Minutes(2),Secinds(10),2)
    ////    lineMap.print()
    ////    //3. 启动 sparkStreaming
    ////    ssc.start()
    ////    ssc.awaitTermination ()
    //  }

    /**
      * 消费者订阅消息，轮询消费数据，消费者必须持续对kafka进行消费，否则被认为是已经死亡。
      * poll(time) time 是指超时时间，用于控制 poll 的阻塞时间，如果该参数被设为0 ，会被立即返回，否则会在指定的毫秒内一直等待broker 返回数据。
      * 轮询是消费者的核心，一旦消费者订阅了主题，轮询就会处理所有的细节，包括群组协调，分区再均衡，发送心跳和获取数据。在第一次调用poll()时，他会负责查找GroupCoordinator ,然后加入群组，接受分配的分区。
      *
      * 消息消费之后 提交 (更新分区当前的位置)commit两种方案
      *       1.同步提交/手动提交:
      *       auto.commit.offset 设为 false
      * commitSync() 提交由poll() 方法最新的偏移量，提交成功便马上返回，提交失败就抛出异常。
      * 缺点: 在broker 底提交请求做出回应之前，程序会一直处于阻塞，会限制应用程序的吞吐量。
      *       2.异步提交/自动提交:
      * commitAsync() 只管发送提交请求，无需等待broker 的响应。
      * 提交最后一个偏移量，继续其他的事情，无需等待broker 响应。
      *       3.同步提交与异步提交的区别
      * 异步提交不会进行重试，同步提交会一直进行重试。
      *
      * @param topicName
      */
    def consumeSync(topicName: String): Unit = {
      //  public void subscribe(Collection<String> topics) {
      //consumer subscribe information
      consumer.subscribe(Collections.singleton(topicName))
      //    consumer.subscribe("test.*")     // 匹配到多个主题。
      // 这是一个无限循环，消费者是一个长期运行的应用程序，通过持续的轮询来向kafka 请求数据，
      while (true) {
        // 读取数据，读取数据的超时时间为 100ms
        val consumerRecords = consumer.poll(100).asScala
        for (record <- consumerRecords) {
          val topic_offset = record.offset()
          val key = record.key()
          val value = record.value()
          val time = record.timestamp()
//          val headers = record.headers()
          val partition = record.partition()
          println(s"${time}下 ${topicName} 的 offset 为: ${topic_offset},key 为: ${key} ,value 为${value},Header 为,Partition 为 ${partition}")
        }
        // 处理完最后一批消息后，使用 commmitSync() 提交偏移量
        // CommitSync()
        // CommitAsync()
        //      try {
        //        consumer.commitSync();
        //      }catch{
        //        case ex:CommitFailedException =>{
        //          println("Commit failed Exception !")
        //        }
        //        case ex:Exception => {
        //          println("Exception !")
        //        }
        //      }finally {
        //        println("Exiting finally ....")
        //      }
      }
    }


    def consumeAsync(topicName: String): Unit = {
      consumer.subscribe(Collections.singleton(topicName))
      while (true) {
        // 读取数据，读取数据的超时时间为 100ms
        val consumerRecords = consumer.poll(100).asScala
        for (record <- consumerRecords) {
          val topic_offset = record.offset()
          val key = record.key()
          println(s"${topicName} 的 offset 为: ${topic_offset},key 为: ${key} ")
        }
        // 处理完最后一批消息后，使用 commmitSync() 提交偏移量
        // CommitSync()
        // CommitAsync(
        // 提交最后一个偏移量，继续做其他的事情。无需等待是否提交成功。
        consumer.commitAsync()
        consumer.close()

      }
    }


    def consumerWithOutGroup(topicName: String): Unit = {
      val partitionInfos = consumer.partitionsFor(topicName).asScala
      var array: ArrayBuffer[TopicPartition] = ArrayBuffer()
      if (partitionInfos != null) {
        // 获取该 topic 的所有分区集合
        partitionInfos.foreach(parti => {
          val topicPartition: TopicPartition = new TopicPartition(topicName, parti.partition())
          array += topicPartition
        })
        // 只有一个消费者，没有消费者组的话
        // 就不需要订阅主题，只需要为自己分配分区
        //=========  一个消费者可以订阅主题（加入消费者组），或者为自己分配分组，但是不能同时做这两件事。
        consumer.assign(array.asJavaCollection)

        val consumerRecords = consumer.poll(100).asScala
        for (record <- consumerRecords) {
          val topic_offset = record.offset()
          val key = record.key()
          val value = record.value()
          val time = record.timestamp()
//          val headers = record.headers()
          val partition = record.partition()
          println(s"${time}下 ${topicName} 的 offset 为: ${topic_offset},key 为: ${key} ,value 为${value},Header 为 ,Partition 为 ${partition}")
        }
      }
      // 同步提交，手动提交。
      consumer.commitSync()

    }
}
