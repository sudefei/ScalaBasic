package sdf.basic

/** Nothing 是所有的子类，但是没有实例
  * option 类是抽象的，有两个子类
  *       Some（有返回值）
  *       None（无返回值）
 */
object OptionDemo {
  def main(args: Array[String]): Unit = {
    //构建一个Map集合
    val books=Map("hadoop"->("Hadoop",35),"spark"->("Spark",33))
    // 有数据则返回 Some，无数据返回None
    // get(key:String) 获取value
    println(books.get("hadoop"));
    //Option[(String,Int)]=Some((Hadoop,35))
    println(books.get("hadoop").get);
    //(String, Int) = (Hadoop,35)
    println(books.get("hive"));
    //Option[(String,Int)]=None
    println(books.get("Spark").get);
    //(String, Int) = (Spark,33)

  }

}
