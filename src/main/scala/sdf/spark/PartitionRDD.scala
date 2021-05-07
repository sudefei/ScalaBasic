package sdf.spark

import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}
/**RDD逻辑上是分区的，每个分区的数据抽象存在，计算的时候通过一个函数计算每个分区的数据
  *分区的好处： 增加并行度，实现分布式计算，减少通讯开销
  *local(本地模式) 默认是本地机器CPU的数目
  *local[N] 设置为 N个
  * Mesos 模式，不设置的话，默认是8
  *
  * conf.setMaster(local[numPartition])
  * 创建RDD
  * sc.parallelize(seq,numPartition)
  * sc.makeRDD(seq,numPartition)
  *
  * 修改分区
  * repartition() 可以修改分区，并且有效
  * coalesce() 能有效的折叠（coalesce） 同一个工作节点的分区，以便在重新分区时同一个WorkNode上避免再次shuffle,使用coalesce 可以将数据数据合并到一个分区，避免shuffle
  * 二者的区别：
  *
  *
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

  def createRDD(): Unit ={

    // 分区的优先级设置  sc.parallelize | sc.makeRDD >  sc.setMaster(local[4])
    val rdd_01 = sc.parallelize(list,2)
    val rdd_02 = sc.makeRDD(list,2)
    val rdd_03 = sc.textFile("/usr/tool/shell/a.txt",2)

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

  /**  RDD 缓存，RRDD之间具有较长的血缘关系，在一个应用程序中，若果经常或者多次使用同一个RDD，
    * 可以将其缓存起来，那么下次调用该RDD时，只有在第一次使用的时候走血缘关系，下次使用的时候不必再根据血缘关系去得到分区的数据，会直接从缓存处取。
    *
    * cache()     cache 内部调用了  persist(StorageLevel.MEMORY_ONLY)
    * persist()   保存有两种 MEMORY_ONLY   : 如果内存不足时，将Old给挤出去
    *                      MEMORY_AND_DISK : 如果内存不足，会将其存到磁盘，不会将old 挤出去
    * checkpoint  是将血缘关系较长的逻辑计算的中间结果缓存到HDFS上，而cache 是缓存在内存中
    *
    *
    * 以上两种缓存方式不是调用缓存就立即被缓存了，而是触发后面的action时，将RDD缓存到内存中，并提供后续的使用
    * 将数据以序列化的方式缓存在JVM的堆内存中
    *
    */
  def rddCache(): Unit ={
    val rdd_01 = sc.parallelize(list,2)
    val rdd_0 = rdd_01.cache()  //res0: rdd_01.type = ParallelCollectionRDD[0] at parallelize at <console>:29

    rdd_01.persist(StorageLevel.MEMORY_AND_DISK)
//  同一个RDD只能被缓存一个 StorageLevel ,再次对该RDD进行缓存，则抛出异常 java.lang.UnsupportedOperationException: Cannot change storage level of an RDD after it was already assigned a level
    rdd_01.persist(StorageLevel.MEMORY_ONLY)
// 可以将该RDD 移除缓存，对其进行缓存一个 StorageLevel
    rdd_01.unpersist()

  }

  def splitRDD(): Unit ={
    val rdd = sc.makeRDD(1 to 12 ,12)
//  该函数根据权重(weight) ,将一个RDD切割成
    //由于randomSplit的第一个参数weights中传入的值有4个，因此，就会切分成4个RDD,
    //把原来的rdd按照权重0.5, 0.1, 0.2, 0.2，随机划分到这4个RDD中，权重高的RDD，划分到//的几率就大一些。
    //注意，权重的总和加起来为1，否则会不正常

    /** def randomSplit(weights: Array[Double],seed: Long = Utils.random.nextLong): Array[RDD[T]] ={}
      */
    var splitRDD = rdd.randomSplit(Array(0.5, 0.1, 0.2, 0.2))
    splitRDD.size
    splitRDD(0)
    splitRDD(1)
    splitRDD(2)
    splitRDD(3)
  }

  def TwoRDDOpt(): Unit ={
    val rdd1 = sc.parallelize(List(5, 6, 4, 3))
    val rdd2 = sc.parallelize(List(1, 2, 3, 4))

    var pair_rdd1 = sc.makeRDD(Array(("A","1"),("B","2"),("C","3")),2)
    var pair_rdd2 = sc.makeRDD(Array(("A","1"),("C","c"),("D","d")),2)
    //    并集  适用于键值对RDD
    rdd1.union(rdd2)
    pair_rdd1.union(pair_rdd2)
    //    交集 适用于键值对RDD
    rdd1.intersection(rdd2)
    pair_rdd1.intersection(pair_rdd2)   // Array[(String, String)] = Array((A,1))
    rdd1.union(rdd2).distinct()
    //    补集 适用于键值对RDD
    rdd1.subtract(rdd2)
    pair_rdd1.subtract(pair_rdd2)      //  Array[(String, String)] = Array((C,3), (B,2))
//subtractByKey和基本转换操作中的subtract类似，只不过这里是针对K的，返回在主RDD中出现，并且不在otherRDD中出现的元素。
    pair_rdd1.subtractByKey(pair_rdd2)   // Array[(String, String)] = Array((B,2))

    pair_rdd1.cogroup(pair_rdd2)
//    scala> val rdd1 = sc.parallelize(Array(("aa",1),("bb",2),("cc",6)))
//    scala> val rdd2 = sc.parallelize(Array(("aa",3),("dd",4),("aa",5)))

    //    def cogroup[W](other: RDD[(K, W)]): RDD[(K, (Iterable[V], Iterable[W]))] = self.withScope {
    //   Array[(String, (Iterable[Int], Iterable[Int]))] = Array((aa,(CompactBuffer(1),CompactBuffer(3, 5))), (dd,(CompactBuffer(),CompactBuffer(4))), (bb,(CompactBuffer(2),CompactBuffer())), (cc,(CompactBuffer(6),CompactBuffer())))

    // 对相同key 的 value 生成一个集合
    pair_rdd1.join(pair_rdd2).collect()
    // res23: Array[(String, (String, String))] = Array((A,(1,a)), (C,(3,c)))




  }

  def mapRDD(): Unit ={

    val rdd = sc.makeRDD(1 to 12 ,12)
    rdd.mapPartitions(
      datas=>{
        datas.filter(_==2)
      }
    )


  }







}
