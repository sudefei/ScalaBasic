package sdf.advance

/**
  * @Author ： defei.su
  * @Date : 2021/5/7 11:54
  */


/** 柯里化会把接收多个参数的函数转化为接收多个参数列表的函数
  * 把接受多个参数的函数变换成接受一个单一参数(最初函数的第一个参数)的函数，
  * 并且返回接受余下的参数且返回结果的新函数的技术
  */
object Currying {

  //多个参数列表定义一个方法
  def foo(a: Int)(b : Int)(c : Int) = {}
  foo _
  // foo _ 创建了一个部分应用函数（就是含有一个或者多个未绑定参数的函数）
  // res0: Int => (Int => (Int => Unit)) = <function1>


  def inject(arr: Array[Int], initial: Int)(operation: (Int, Int) => Int): Int = {
    var carryOver = initial
    arr.foreach(element => carryOver = operation(carryOver, element))
    carryOver
  }
  inject _
  // res8: (Array[Int], Int) => ((Int, Int) => Int) => Int = <function2>


  def twoFunc(x:Int,y :Int) = x + y
  twoFunc _
// res12: (Int, Int) => Int = <function2>
  def oneFunc(z:Int) = twoFunc(z ,1 )
  oneFunc _
// res11: Int => Int = <function1>
  def zero = oneFunc(2)
  zero _
// res10: () => Int = <function0>

  def z = (x :Int) => (y :Int) => x + y
// res13: () => Int => (Int => Int) = <function0>

}
