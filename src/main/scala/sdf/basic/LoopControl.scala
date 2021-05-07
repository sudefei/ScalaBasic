package sdf.basic


import util.control.Breaks._

/**
  * @Author defei.su
  * @Date 2021/3/28 18:45
  */
object LoopControl {
  /** scala 中没有break 和continue
    * 只有一个Breaks 类，通过该类实现 循环的控制
    *
    */
  val arr = Array(1,3,10,5,4)

  def breakControl(): Unit ={

    breakable{

       for (i <- arr){
         if(i > 5)break //跳出循环，相当于Java中的 break ,
         println(i)
       }

    }

    // 最终的输结果是 ：  1,3
  }


  def continueControl(): Unit ={
    for (i <- arr){
      breakable{
        if(i > 5)break //跳出breakable ,终止当次循环，相当于Java 中的 Continue
        println(i)
      }
    }
    // 最终的输出结果是 ： 1,3,5,4
  }






}
