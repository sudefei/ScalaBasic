package com.sdf.test

import org.junit.Test
import sdf.kafka.{ KafkaAllUtils }

/**
  * @Author defei.su
  * @Date 2020/8/27 10:34
  */
class KafkaTest {

  @Test
  def testoffset(): Unit ={
    val before = KafkaAllUtils.getEndOffset("kafka_test_topic")
    println("send 之前"+before)

    KafkaAllUtils.sendToKafka("kafka_test_topic","kafka 的 Key 主要组成 生产者，消费者，topic 等等","kafka 的值")
    KafkaAllUtils.sendToKafka("kafka_test_topic","A B C D :String Key","kafka 的值：ABCD Value")
    KafkaAllUtils.sendToKafka("kafka_test_topic","kafka 是一个消息队列","kafka 的值：消息队列 Value")

    val later = KafkaAllUtils.getEndOffset("kafka_test_topic")
    println("send 之后"+later)
  }

  @Test
  def testConsume(): Unit ={
    KafkaAllUtils.consumeSync("kafka_test_topic")
//    KafkaAllUtils.consumeAsync("MCMES_STREAMSETS")

  }



}
