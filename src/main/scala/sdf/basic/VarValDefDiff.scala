package sdf.basic

/**
  * @Author defei.su
  * @Date 2021/1/4 15:41
  */
object VarValDefDiff {

  def main(args: Array[String]): Unit = {
    caseClassDiff()
  }

  /**  var ： 可变(对象式)，声明式可以赋值，可被多次赋值       变量声明关键字，变量值可以更改，但是变量类型不能更改
    *  val ： 不可变（函数式），声明时必须赋值,只能被赋值一次   常量声明关键字
    *  lazy val : 懒性 val ,当需要计算时才使用，避免重复计算
    *  def ： 定义函数
    */
  def varValDiff(): Unit ={
    var msg ="Hello World !"
    msg = "Hello World Again"
    print(msg)

    val message = "Hello Scala"
    // 对 Val进行赋，编译失败，Val不能被重新赋值
//    message = "Hello Scala Again"

    // 懒执行，只有在需要计算时才使用，避免重复计算
    lazy val meg ="Hello"
  }



  /**
    *   结论：构造器中val修饰的参数，编译之后会在该类中添加一个private final的全局常量，同时提供一个用于获取该常量的public方法，无设置方法。
    * 　　　构造器中var修饰的参数，编译之后会在该类中添加一个private的全局变量，同时提供两个获取和设置该变量值的public方法。
    *  　　　构造器中无修饰符的参数，则该参数属于构造函数内的局部参数，仅仅在该构造函数内部访问。
    */
class AA(name:String)
class BB(val name:String)
class CC(var name:String)

case class DD(name:String)
//  三者目前还没有区别，反编译之后出现不同
  /**
    * public class AA {
    *  public AA(java.lang.String); }
    */

  /**
    * public class BB {
    *   private final java.lang.String name;
    *   public java.lang.String name();
    *   public BB(java.lang.String);
    * }
    */

  /**
    * public class CC {
    *    private java.lang.String name;
    *   public java.lang.String name();
    *  public void name_$eq(java.lang.String);
    *  public CC(java.lang.String);
    * }
    */

  /** case class 也是一个普通class，不同在以下几点
   */

  def caseClassDiff(): Unit ={

    /**  1. case class 初始化的时候可以不用 new ,当然也可以加上，但是普通类就一定需要 new */
    val aa =new AA("hdfs")
    val dd =DD("yarn")

    /** 2. case class 直接实现了 toString ,而 普通类没有 */
    println(aa)
//    sdf.basic.VarValDefDiff$AA@880ec60
    println(dd)
//     DD(yarn)
    val aa2 = new AA("hdfs")
    val dd2 =DD("yarn")

    println(aa == aa2)   //true
    //  二者均是新 new 的对象，且没有实现 hashCode和 equals 方法
    println(dd == dd2)   // false
    //  case class 实现了 equal 和 hashCode , case class 的是同一个对象，hashCode相同，因此二者相等

    /** 4. case class 构造函数的参数是 public 级别的，则可以直接调用 */
    println(dd.name)
    /** 3. 认实现了equals 和hashCode */
//    aa.name  error: value name is not a member of AA

    /** 5. case class 可以进行模式匹配  具体请见  sdf.basic,Match
           模式匹配是 case class 最主要的功能
       */

  }

}
