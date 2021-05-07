package sdf.advance

/**
  * @Author ： defei.su
  * @Date : 2021/5/7 14:38
  */

object Underline {

  /*** 无处不在的下划线
    *  1. 作为包引入的通配符   例如 import java.util._  相当于 import java.util.*
    *  2. 作为元组索引的前缀   例如 val names = ("Tom", "Jerry") ,可以使用 names._1 或者 names._2 获取元组的值
    *  3. 作为函数值的隐式参数 例如 ist.map { _ * 2 }和 list.map { e => e *2 }是等价的
    *  4. 用于用默认值初始化变量  val min : Int = _ 是用0 初始化变量min ,val msg : String = _ 是用 null 初始化变量msg
    *  5. 用于模式匹配时的通配符  case _ 相当于Java中的 default,匹配任意类型的值
    *                             case _:Int 匹配所有整数
    *  6. 作为分解操作的一部分 例如 max(arg: _*)在将数组或者列表参数传递给接受可变长度参数的函数前，将其分解为离散的值
    */

}
