package sdf.basic

import scala.collection.mutable.ArrayBuffer

object MultiArray {
  def main(args: Array[String]): Unit = {
    // for 循环可以有返回值，并将返回值赋值给变量
    val arr=for(i <- Array(1,2,3,4,5) if i%2==0) yield i;
    println("arr 数组为："+ arr)


    val matrix=Array.ofDim[Int](3,4)
    createMatrix(matrix);
    printMatrix(matrix);
    print(matrix(2)(1))
//
//    forTo()
//    forUtil()
//    forIf()
//    forVarible()
//    forYield()

//    arrayOperation()
    listOperation()
  }

  def createMatrix(matrix:Array[Array[Int]]):Unit ={
    //给 matrix 进行赋值
    for (i<- 0 to 2){
      for(j <- 0 to 3){
        matrix(i)(j) = j;
      }
    }
  }

  def printMatrix(matrix:Array[Array[Int]]):Unit = {
    //打印二维矩阵
    for (i<- 0 to 2){
      for(j <- 0 to 3){
        print(" "+matrix(i)(j));
      }
      println();
    }
  }


  def forTo(): Unit ={
    println("==================> for to")
    // to 左右两边是前闭后闭的访问
    //1 ,2,3
    for (i <- 1 to 3; j <- 1 to 3){
      print(i * j + "")
    }
    println()
  }

  def forUntil(): Unit ={
    println("=================> for until")
    // util 左右两边为前闭后开的访问
    // 1 ，2
    for (i <- 1 until  3; j <- 1 until  3){
      print(i * j + "")
    }
    println()
  }

  def forIf(): Unit ={
    // 条件推导式,满足条件的进行循环，不满足条件的就退出循环，开始下一次循环（相当于 continue）
    println("===============>   for if ")
    for (i <- 1 to 5 if i %2==0){
      print(i+" ")
    }
    println()
  }

  def forVarible(): Unit ={
    println("=================>  for varible")
    for (i <- 1 to 3;j = 10- i){
      println(j +" ")
    }
    println()
  }

  def forYield(): Unit ={
    println("==================>   for yield")
    val foryield = for (i <- 1 to 5 if i %2 == 0) yield i
    println(foryield)

  }

  def arrayOperation(): Unit ={
    //数据结构
    // 定长数组
    val intValue=new Array[Int](3);
    val strValue=new Array[String](3);
    val defineValue=new Array[String](5);
     intValue(1)=7
    val arr=Array(1,2,3)
    println("arr 的值为:"+ arr)
    // 变长数组
    val arr2=ArrayBuffer[Int]()
    arr2.append(2)
    // 赋值
    arr2(0)=10
    // 定长数组与 变长数组之间的数据转换
    println("arr  to  Buffer ==========>"+arr.toBuffer)
    println("arr2 to Array ============>"+arr2.toArray)
    // 多维数组
    val array = Array.ofDim[String](3,4,5)
    array(2)(1)(1)="hello"
    println("多维数组 array ===========>"+array(2)(1)(1))
  }

  def listOperation(): Unit ={
    val list1=List(1,2,3,4)
    println("list1 Value=======>"+list1)
    //获取list 的 某一个元素
    println("list1 的某个 Value=======>"+list1(1))
    val list2 =list1 :+ 99
    println("list2 Value=======>"+list2)
    val list3 =100 +: list1
    println("list3 Value=======>"+list3)
    val list4= 1 :: 2 :: 3 :: list1 :: Nil
    println("list4 Value=======>"+list4)
    val list5=list1 :: list2 :: list3 :: list4 :: Nil
    println(list5)

  }

  }
