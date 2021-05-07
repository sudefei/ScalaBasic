package sdf.basic

/** Nothing 是所有的子类，但是没有实例
  * Option 类是抽象的，有两个子类
  *       Some（有返回值）
  *       None（无返回值）
  *  Option.get  必须是非None的，否则会抛出异常
  *  /** Returns the option's value.
  * *  @note The option must be nonEmpty.
  * *  @throws Predef.NoSuchElementException if the option is empty.
  * */
  *  def get: A
 */
object OptionDemo {
  def main(args: Array[String]): Unit = {
    //构建一个Map集合
    val books=Map("hadoop"->("Hadoop",35),"spark"->("Spark",33))
    // 有数据则返回 Some，无数据返回None
    // get(key:String) 获取value
    val maybeTuple: Option[(String, Int)] = books.get("hadoop")
    //Option[(String,Int)]=Some((Hadoop,35))
    val value: (String, Int) = books.get("hadoop").get
    // apply() 内部调用了 get("key") 并根据返回值类型进行匹配，返回值为None , 则会抛出异常
    //                                                         返回值为Some，则返回Some里的value
    val tuple: (String, Int) = books.apply("hadoop")
    books.apply("Hadoop")
//  java.util.NoSuchElementException: key not found: Hadoop
    //def apply(key: A): B = get(key) match {
//      case None => default(key)
//      case Some(value) => value
//    }
    //(String, Int) = (Hadoop,35)
    println(books.get("hive"));
    //Option[(String,Int)]=None
    books.get("hive").get
    // java.util.NoSuchElementException: None.get
    println(books.get("Spark").get);
    //(String, Int) = (Spark,33)

  }

}
