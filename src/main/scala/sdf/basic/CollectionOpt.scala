package sdf.basic

import java.awt.Color

import scala.collection.{LinearSeq, SortedSet, mutable}

/**
  * @Author defei.su
  * @Date 2021/1/4 16:20
  */

/** 集合是不包含重复元素的可迭代对象
  */

object CollectionOpt {

  def main(args: Array[String]): Unit = {
//    mutableSetOpt()
//    mapOpt()
    ListOpt()

  }
  // 不可变集合，那么在不同的节点访问同一个集合的值，得到的结果都是一致的
  /**
    * 顶级父类/根集合： scala.collection
    * 不可变集合：scala.collection.immutable
    * 可变集合：scala.collection.mutable
    *
    * 默认的是 不可变集合，定义可变集合 ，需要 import scala.collection.mutable
    *
    * 可变集合可以修改，添加，移除
    * 不可变集合永远不会改变，但是可以模拟添加，移除和更新操作，返回的都是新集合
    */
  def defineCollection(): Unit ={

    val x="hello"
    val y="java"
    val z="scala"

    val a=""
    val b=""
    val c=""

    val ints = Traversable(1, 2, 3)
    val iterable = Iterable("x", "y", "z")
    val stringToInt = Map("x" -> 24, "y" -> 25, "z" -> 26)
    val colors = Set(Color.red, Color.green, Color.blue)
    val sortedSet = SortedSet("hello", "world")
    val buffer = mutable.Buffer(x, y, z)
    val doubles = IndexedSeq(1.0, 2.0)
    val linerSeq = LinearSeq(a, b, c)
  }

  /** 加法 ： + 和 ++ ，添加一个或者多个元素到集合中，生成一个新的集合
    * 减法 ： - 和 -- , 移除一个或者多个元素从集合中，生成一个新集合
    * Set 运算交集,并集，差集
    */
  def SetOperation(): Unit ={
    // 是否包含  xs contains x / xs(x) / xs subsetOf ys
    val xs = Set("hadoop","spark","yarn","kafka")
    val ys = Set("scala")
    val yarnBoolean = xs ("yarn")
    val javaBoolean = xs.contains("java")
    val ysBoolean = xs.subsetOf(ys)

    // 加法  xs + x / xs +(x,y,z) /xs ++ ys
    val addHello = xs + "hello"
    val javaAdd = xs + ("java","javascript","html")
    val xys = xs ++ ys

    // 移除  xs -x / xs -- ys
    val descrine = addHello - "hello".toLowerCase()
    val x = xys  -- ys


    // 两个数据集的 交集，并集，差集
    // 交集  xs & ys /  xs intersect ys
    val intsersection = xs & ys
    val intersect_tion = xs intersect ys
    val intersect_tion_2 = xs.intersect(ys)

    // 并集
    val unionSet = xs | ys
    val unionSet_tion = xs.union(ys)
    val unionSet_tion_2 = xs union ys

    // 差集
    val diff = xys &~ xs
    val diff_tion = xys.diff(ys)
    val diff_tion_2 = xys diff xs

  }

  /**
    *  mutable  可变集合 、  immutable 不可变集合
    *  var  可变变量     、  val  不可变变量
    */
  def mutableSetOpt(): Unit ={
    //  不可变集合 赋值 给可变变量
    var bigData = Set("hadoop","spark","yarn","kafka")
    val language = Set("scala","java","python")
    val mutableSet = collection.mutable.Set("sqoop","pig","sql")

    // 以下两种方式等价
    bigData -= "hadoop"
    // 可变集合 特有方法  xs remove x 返回值为true(之前有) 或者 false(之前没有)
    // 可变集合执行该操作之后，集合发生变化，数据增加或者被删除
    // 不可变集合必须重新赋值给 定义的可变变量
    mutableSet remove "sql"

    bigData = bigData - "hadoop"
    bigData -= ("flume","flink"," solr")
    bigData --= language
    // 可变集合特有方法  xs retain P  ，仅保留满足条件 P 的元素
//    mutableSet.retain(P)
    print(bigData)

    bigData += "hdfs"
    // 可变集合 特有方法  xs add x 返回值为true(之前没有) 或者 false(之前有)
    mutableSet add "sql"

    bigData += ("flume","flink"," solr")
    bigData ++= language
    print(bigData)

    // 清除数据
    mutableSet.clear()
    // 可变集合特有方法 克隆一个与目标集合数据元素相同的 集合
    mutableSet.clone()

  }

  /**
    * Map 集合是键值对类型
    * 以下是对Map的 增删改查操作
    */
  def mapOpt(): Unit ={
    val valMap = Map("hadoop"->1,"hdfs"->2,"yarn" -> 3)
    val mutableMap =collection.mutable.Map("java"-> 11, "scala"->22)


    // 获取 Key Value 的各种形式
    val keys:Iterable[String] = valMap.keys
    val set:Set[String] = valMap.keySet
    val key_iterator:Iterator[String] = mutableMap.keysIterator
    val values:Iterable[Int] = valMap.values
    val value_iterator:Iterator[Int] = mutableMap.valuesIterator

    // 查询功能
    valMap.get("hadoop")   // 返回值为 Option ,包含返回 Some ,不包含返回 None
    valMap("hadoop")       // 包含直接返回value , 不包含直接抛出异常
    valMap getOrElse ("java","28") //包含返回原本值，不包含则返回 28
    valMap contains("java")   //返回值为 Boolean ,有则返回true ,无则返回 false
    valMap.isDefinedAt("java") // 同 contains
    println(valMap)

    //添加及更新
    mutableMap("python") = 14   // 向 Map 集合中新增
    mutableMap += ("C++"-> 44)   // 新增
    mutableMap.put("R",55)
    mutableMap.getOrElseUpdate("R",66)
    println(mutableMap)

    // 移除
    mutableMap -= "R"
    mutableMap.remove("R")
    println(mutableMap)

    // 克隆
    mutableMap.clone()
  }


  def ListOpt(): Unit ={
    val  list = List(1,2,3,4,5,6)
    println(list.addString(new StringBuilder("hello")))
//    hello123456
    println(list.addString(new StringBuilder("hello"),"e"))
    //    hello1e2e3e4e5e6
    println(list.addString(new StringBuilder("hello"),"h","e","o"))
    //    helloh1e2e3e4e5e6o

    // 添加
//    Nil  长度为 0 的List
    val nums = 1 :: (2:: (3:: (4 :: Nil)))
    //由于::操作符的优先级是从右向左的，因此上一条语句等同于下面这条语句
    val  nums_01 = 1 :: 2 :: 3 :: 4 :: Nil
    nums.+:(5)  // List[Int] = List(5, 1, 2, 3, 4)
    nums.:+(5)  // List[Int] = List(1, 2, 3, 4, 5)
    nums ++ nums_01  //List[Int] = List(1, 2, 3, 4, 1, 2, 3, 4)
    nums ::: nums_01   //List[Int] = List(1, 2, 3, 4, 1, 2, 3, 4)
    nums.head        // 获取第一个  1
    nums.tail       //  获取除第一个以外的所有元素 2,3,4
    nums.init       //  获取除最后一个以外的所有元素 2,3,4
    nums.last       //  获取最后一个

    nums.take(1)    // 获取第几个元素
    nums.drop(2)    // 删除某个元素
    nums.dropWhile(x => x % 2  == 0)
    nums.takeRight(2)    // 取右边的几个  3,4
    nums.takeWhile(x => x %2 == 0 )  // 只取满足条件的
    nums.reverse     // 翻转列表

    nums.toList   //List[Int] = List(1, 2, 3, 4)
    nums.toString()  //String = List(1, 2, 3, 4)
    nums.toArray     //Array[Int] = Array(1, 2, 3, 4)
    nums.toIterable  // Iterable[Int] = List(1, 2, 3, 4)
    nums.toSeq       //  scala.collection.immutable.Seq[Int] = List(1, 2, 3, 4)
    nums.toVector    // Vector[Int] = Vector(1, 2, 3, 4)
    nums.toStream    //  scala.collection.immutable.Stream[Int] = Stream(1, ?)

    /** List 伴生对象方法
      */
    // apply 方法
    List.apply(1,2,3,4)
     // Range 等差序列 ，不包含右边
    List( 1 to 5 )  //List[scala.collection.immutable.Range.Inclusive] = List(Range(1, 2, 3, 4))
    List.range(1,5)  //List[Int] = List(1, 2, 3, 4)
    List.range(2,6,2)
    List.range(2,6,-1)  // List[Int] = List()
    List.range(6,2,-1)  // List[Int] = List(6, 5, 4, 3)

  }



}
