package sdf.basic

object Match {
  val grade= List(77,88,99,100)
  val importantPeopleInfo = Seq("15938911965","sudefei_086@163.com")

  def main(args: Array[String]): Unit = {
    val someSms = SMS("12345", "Are you there?")
    val someVoiceRecording = VoiceRecording("Tom", "voicerecording.org/id/123")
    println(matchCaseClass(someSms))
    println(matchCaseClass(someVoiceRecording))

    val importantEmail = Email("sudefei_086@163.com", "Drinks tonight?", "I'm free after 5!")
    val importantSms = SMS("15938911965", "I'm here! Where are you?")

    println(matchCaseClassWithIf(importantEmail,importantPeopleInfo))
    println(matchCaseClassWithIf(importantSms,importantPeopleInfo))
    println(matchCaseClassWithIf(someVoiceRecording,importantPeopleInfo))


  }

  def matchString(): Unit = {
  val sign = readLine ("Please put in your color:");
  //println("Please put in your grade:")
  //val grade=readChar()
  sign match {
  case "red" => print ("Stop")
  case "green" => print ("Go")
  case "yellow" => print ("Wait")
  case _ => print ("Unkown Value") //相当于是switch 中的default
}
  //match 匹配既可以输出，还可以进行赋值
  val action = sign match {
  case "red" => "Stop"
  case "green" => "Go"
  case "yellow" => "Wait"
  case _ => "Unknown Value" //相当于是switch 中的default
}
  println (action)
}


  def matchFormat(): Unit ={
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
  }

  def matchGrade(): Unit ={
    println("Please put in your grade")
    val grade=readChar()
    grade match{
      case 'A'=>println("80-100")
      case 'B'=>println("60-80")
      case 'C'=>println("40-60")
      case 'D'=>println("0-40")
      case _=>println("error input char")
    }

    def matchVariable(): Unit ={
      //case 中可以定义守卫（guard）,守卫是指每次操作中添加条件表达式
      val v=3
      v match {
        case v if (v>0 && v<5) => println("0-4")
        case v if (v>=5 && v<10) => println("5-9")
        case _=> println("not found")
      }
    }


  }

  abstract class Notification

  case class Email(sender: String, title: String, body: String) extends Notification

  case class SMS(caller: String, message: String) extends Notification

  case class VoiceRecording(contactName: String, link: String) extends Notification

  def matchCaseClass(notifacation: Notification): String ={
    notifacation match {
      case Email(email, title, _) =>
        s"You got an email from $email with title: $title"
      case SMS(number, message) =>
        s"You got an SMS from $number! Message: $message"
      case VoiceRecording(name, link) =>
        s"you received a Voice Recording from $name! Click the link to hear it: $link"
    }
  }

  def matchCaseClassWithIf(notifacation: Notification,importPeople :Seq[String]): String ={
    //case 中可以定义守卫（guard）,守卫是指每次操作中添加条件表达式
    notifacation match {
      case Email(email, _, _) if importantPeopleInfo.contains(email) =>
        s"You got an email from special people $email"
      case SMS(number, _)  if importantPeopleInfo.contains(number)=>
        s"You got an SMS from  special number $number"
      case other =>
        matchCaseClass(other)
     }
  }

}