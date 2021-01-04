package sdf.basic

object Match {
  def main(args: Array[String]): Unit = {

  val sign=readLine("Please put in your color:");
  //println("Please put in your grade:")
  //val grade=readChar()
  sign match {
    case "red" => print("Stop")
    case "green" => print("Go")
    case "yellow" => print("Wait")
    case _=> print("Unkown Value")    //相当于是switch 中的default
  }

  //match 匹配既可以输出，还可以进行赋值
  val action=sign match {
    case "red" => "Stop"
    case "green" => "Go"
    case "yellow" => "Wait"
    case _=> "Unkown Value"    //相当于是switch 中的default
  }
  println(action)

  println("Please put in your grade")
  val grade=readChar()
  grade match{
    case 'A'=>println("80-100")
    case 'B'=>println("60-80")
    case 'C'=>println("40-60")
    case 'D'=>println("0-40")
    case _=>println("error input char")
  }

  for (elem<- List(6,7,7.7,"String",'C',grade)){
    val str=elem match{
      case i:Int => i+"is an int value"
      case d:Double => d+"is a double value"
      case "Spark" =>  "Spark is not find"
      case s:String => s+"is a String value "
      case _=> "unexpected value format"
    }
    println(str)
  }
    //case 中可以定义守卫（guard）,守卫是指每次操作中添加条件表达式
  val v=3
    v match {
      case v if (v>0 && v<5) => println("0-4")
      case v if (v>=5 && v<10) => println("5-9")
      case _=> println("not found")
    }
}
}