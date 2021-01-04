package sdf.basic

object MultiParameter {


  def main(args: Array[String]): Unit = {
     val addition=add(1,2,3,4)
     println(addition)

    val str = mutilString("hello","world","sdf","hadoop")
    println(str)

  }
  def add(x:Int*):Int={
    // val:是不可变的元素  var:可变的元素
    var total=0;
    for (i <- x){
      total +=i
    }
     return total
  }


  def mutilString(s:String*): String ={

    var string:String=null;
    for (i <- s){

      string+=s
    }

    return string
  }

}
