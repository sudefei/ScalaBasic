package sdf.basic

import java.io.PrintWriter

import scala.io.Source

object WriterReader {

  def main(args: Array[String]): Unit = {
//    写文件
     //每新建一个 printwriter ，每次的写入都是删除旧文件，新建文件，文件 overwrite into
      val outputFile= new PrintWriter("test02.txt");
      outputFile.write(" Write: Jack Rose");
      outputFile.println("Println: Dnk");
      outputFile.close();

//    读文件
      //加载文件
      val inputFile=Source.fromFile("test02.txt");
      // getlines 返回的是一个迭代器，因此对该迭代器进行遍历
      val lines=inputFile.getLines();
      while(lines.hasNext){
        System.out.println(lines.next());
      }
  }

}
