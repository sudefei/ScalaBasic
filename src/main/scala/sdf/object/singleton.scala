package sdf.`object`

/** 由于 scala 中没有 static 关键字，那么如何实现 静态呢
  *    在同一个scala 文件中，定义同名的一个类和一个 Object
  *    二者就是伴生类和伴生对象，可以互相访问对方私有的 field
  *
  *
  * 单例对象不能 new ,所以也没有构造参数
  * 单例对象只有在第一次访问时才会被初始化
  */


object food {
  def getFood(){
    println("this is not the same")
  }

  def apply: food = new food()
}

class food{
  def getFood(): Unit ={
    println("this is your food.")
  }
  def info():Unit={
    println("this is info")
  }
}
