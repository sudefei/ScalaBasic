package sdf.spark

import org.apache.spark.{SparkConf, SparkContext}

object PairedRDD {
  /*** PairRDD指的是 （key,value） 类型的RDD
    * PairRdd 有很多特有的方法，常见的操作有 reduceByKey() | groupByKey() | keys() | values() | combineByKey() | join()
    * | mapValues(func) |sortByKey() | sortBy(指定字段)
    */
  val conf=new SparkConf().setAppName("RddPartition").setMaster("local[2]")
  val sc=new SparkContext(conf)
  val pairRDD=sc.parallelize(Array(("hadoop",1),("spark",2),("hive",4)))

  def main(args: Array[String]): Unit = {

    // get keys   : return result type RDD
    pairRDD.keys.foreach(println)

    // get values : return result type RDD
    pairRDD.values.foreach(println)

    // mapValues(func)  文件中的Key不变，对value进行函数操作
    pairRDD.mapValues(x=>x+1)

    // groupByKey()  仅仅是对数据根据key 进行一个分组
    // Array[(String, Iterable[Int])] = Array((java,CompactBuffer(4)), (spark,CompactBuffer(2)), (hadoop,CompactBuffer(3, 1)))
    pairRDD.groupByKey()

    // reduceByKey() 根据key,进行分组且对value进行函数操作
    pairRDD.reduceByKey(_+_)
    pairRDD.reduceByKey((a,b)=>a+b)

    // sortByKey 只能根据key 进行排序，默认是升序
    // finalRDD: Array[(String, Int)] = Array((a,2), (b,1), (d,3), (e,1))
    pairRDD.reduceByKey(_+_).sortByKey(false)

    // sortBy(指定根据 key 还是 value 进行排序) 默认是升序
    pairRDD.reduceByKey(_+_).sortBy(_._2,false)
    val reduceRDD=pairRDD.reduceByKey(_+_).sortBy(_._2,false)

    // join() 把几个PairRDD中包含相同的Key的RDD进行合并,value为数组
    //Array[(String, (Int, Int))] = Array((d,(3,3)), (b,(1,1)), (e,(1,1)), (a,(2,2)))
    pairRDD.join(reduceRDD)

  }


  def avgBookSale(): Unit ={
    /** key: bookName
      * value: everyday sale
      * method: every book every day sale
      */
    val rdd=sc.parallelize(Array(("spark",2),("hadoop",3),("hive",1),("spark",1)))
    // mapValues(func) 对key 不变，对value 进行function操作
    //Array[(String, (Int, Int))] = Array((spark,(2,1)), (hadoop,(3,1)), (hive,(1,1)), (spark,(1,1)))
    val mapRDD=rdd.mapValues((_,1))
    val reduceRDD=mapRDD.reduceByKey((x,y)=>(x._1+y._1,x._2+y._2))
    val mapperRDD=reduceRDD.mapValues(x=>x._1 / x._2).collect()
    mapperRDD.foreach(println)
  }

}
