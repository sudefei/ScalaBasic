package sdf.advance

/**
  * @Author ： defei.su
  * @Date : 2021/5/6 15:46
  */

/** isInstanceOf :  检查某个对象是否属于给定的某个类
  * asInstanceOf ： 将引用转化为子类的引用(类型转换)
  * classOf ：
 **
 *注： 1. 对象类型转换之前最好是判断一下该对象是否为指定类的实例，直接使用 asInstanceOf() 则可能会抛出异常
       *2. 如果对象是 null ,则isInstanceOf 一定会返回 false , asInstanceOf 一定会返回 null
  */
//  scala                 |  java
// obj.isInstanceOf[Class]   obj instanceof Class
//
// obj.asInstanceOf[Class]    (Class)obj
//
// classOf[Class]             Class.class

object InstanceOF {

  class SuperClass{
  }

  class SubClass extends SuperClass{
  }

  def main(args: Array[String]): Unit = {
     // class 引用时需要 new ,case class 引用时不需要
     val testA: SuperClass = new SuperClass
    val testB: SuperClass = new SubClass
    val testC: SubClass = new SubClass

    println(testA.isInstanceOf[SuperClass]) //    true
    println(testB.isInstanceOf[SuperClass]) //    true
    println(testC.isInstanceOf[SubClass]) //    true

    println(testA.asInstanceOf[SuperClass]) //    com.sdf.advance.InstanceOF$SuperClass@11531931
    println(testB.asInstanceOf[SuperClass]) //    com.sdf.advance.InstanceOF$SubClass@5e025e70
    println(testB.asInstanceOf[SubClass])   //    com.sdf.advance.InstanceOF$SubClass@5e025e70
    println(testC.asInstanceOf[SuperClass])   //    com.sdf.advance.InstanceOF$SubClass@1fbc7afb


  }


}
