package sdf.basic

import scala.util.matching.Regex

/**
  * @Author defei.su
  * @Date 2020/10/10 15:06
  */
object RegexFunction {
  def main(args: Array[String]): Unit = {
    StringRCreateRegex()
    newRegexCreateRegex()

  }


  def StringRCreateRegex(): Unit ={
    println("===================================================")
    val pattern = "Scala".r
    val str="Scala is Scalable and cool"
    println(pattern.findAllIn(str).mkString(","))
    println(pattern.findAllMatchIn(str).mkString(","))
      println(pattern.findFirstIn(str))
      println(pattern.findFirstMatchIn(str))
      println(pattern findPrefixMatchOf(str))
      println(pattern findPrefixOf(str))
    println(pattern replaceAllIn(str,"ReplaceAll"))
    println(pattern replaceFirstIn(str,"ReplaceFirst"))
  }

  def newRegexCreateRegex(): Unit ={
    println("===============================================")
    val pattern=new Regex("(S|s)cala")
    val str="Scala is scalable and cool"
    println(pattern.findAllIn(str).mkString(","))
    println(pattern.findAllMatchIn(str).mkString(","))
    println(pattern findFirstIn(str))
    println(pattern findFirstMatchIn(str))
    println(pattern findPrefixMatchOf(str))
    println(pattern findPrefixOf(str))
    println(pattern replaceAllIn(str,"ReplaceAll"))
    println(pattern replaceFirstIn(str,"ReplaceFirst"))
  }




}
