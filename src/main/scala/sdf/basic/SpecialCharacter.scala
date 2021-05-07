package sdf.basic

object SpecialCharacter {


  def main(args: Array[String]): Unit = {
     val addition=add(1,2,3,4)
     println(addition)

    val str = mutilString("hello","world","sdf","hadoop")
    println(str)

  }

  /**
    *    '* '表示多个， '_'  表示仅出现一次
    */
  def add(x:Int*):Int={
    // val:是不可变的元素  var:可变的元素
    var total=0;
    for (i <- x){
      total +=i
    }
    total
  }


  def mutilString(s:String*): String ={
    var string:String=null;
    for (i <- s){
      string+=s
    }
   string
  }

  def onlyChar(): Unit ={
    (1 to 9).filter(_ % 2 == 0)
    // res0: scala.collection.immutable.IndexedSeq[Int] = Vector(2, 4, 6, 8)
     (1 to 3).map(_ * 3)
    // res1: scala.collection.immutable.IndexedSeq[Int] = Vector(3, 6, 9)

  }

}
