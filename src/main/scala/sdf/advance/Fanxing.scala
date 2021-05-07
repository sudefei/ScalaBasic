package sdf.advance

import scala.reflect.ClassTag

/**
  * @Author ： defei.su
  * @Date : 2021/5/6 16:56
  */
object Fanxing {


  def main(args: Array[String]): Unit = {
    mkArray(45,12,45,33).foreach(print)
    mkArray("a","b","c","d").foreach(print)
    mkArray('a','b','c','d').foreach(println)

    makeArray(45,12,45,33).foreach(print)
    makeArray("a","b","c","d").foreach(print)
    makeArray('a','b','c','d').foreach(print)

  }

  // 参数化类型 ，参数类型不固定
  //  ClassTag[T]保存了泛型擦除后的原始类型T,提供给被运行时的。
  //  ClassTag : 在运行时指定，在编译时无法确定
  def mkArray[T:ClassTag](elements:T*) = Array[T](elements:_*)


  //  数组在声明时要求必须指定数据类型，在函数泛型是无法知道数据类型，
  //  通过Manifest关键字使得运行时可以根据这个Manifest参数做更多的事情
  //  Manifest : 需要运行时存储T的实际类型，运行时是作为参数运行在方法的上下文
  //  Manifest ：数组在定义是必须知道具体的类型，所以在申明时，需要指定 Mainfest

  def makeArray[T:Manifest](elements:T*) = Array[T](elements:_*)



}
