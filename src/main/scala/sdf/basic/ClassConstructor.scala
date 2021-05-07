package sdf.basic

class ClassConstructor(){
  /* class 就是构造器 [主构造器 | 辅构造器]
  主构造器 : 最初定义类时的构造器
  辅构造器 : 重写构造方法，辅构造器的名称都为 this
  (辅助构造器的名称为 this)
  每个辅助构造器 必须定义此前已经定义的 辅助构造器或者主构造器

  方法中不能用val 、var 修饰变量，但是类的定义中可以。
  // def add(name:String)    class studentAdd(var name:String)

   */
  var name :String = _
  var step :Int = _
  var dept :String = _



  def this(name: String) {
    this() //调用主构造器
    this.name = name
    printf("the first name is:%s\n", name)
  }

  def this(name: String, step: Int) {
    this(name) //调用之前已经定义好的辅助构造器
    this.step = step
    printf("the second name:%s , step:%d\n", name, step)
  }

  def this(name: String, step: Int,dept: String) {
    this(name,step) //调用之前已经定义好的辅助构造器
    this.step = step
    this.dept =  dept
    printf("the second name:%s , step:%d， dept:%s", name, step,name)
  }


   //构造器的调用
  val conMain=new ClassConstructor();
  val conTwo=new ClassConstructor("sdf")
  val conThree=new ClassConstructor("sdf",2)


}

