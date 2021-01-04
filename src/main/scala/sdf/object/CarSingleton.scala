/**apply 对象主要应用于工厂生产类，生产对象
  * 1.对类创建一个伴生对象
  * 2.在伴生对象中 调用伴生类的构造方法以 apply 方法的形式，写入到伴生对象中
  * 3.伴生对象的方法 apply 会被自动调用
  * 4.调用的时候会生成类对象
  */

/** apply 方法的主要目的是 使 函数式编程 和 命令式编程保持一致
  * 使用方法时可以一致，可以相互使用
  */

class Car(val brand:String,val price:Int){
  def info(){
    println("Car brand is:" + brand+"price is:" + price)}}

/*** unapply 可以想象成是 apply 的反向方法，
  *  apply 是通过传递参数，创建对象
  *  unapply 是通过传递对象，通过对象拿到相对应的参数值
  */
object Car{
  def apply(brand:String,price:Int)={
    println("Debug:calling apply ...")
    new Car(brand,price)}

  def unapply(c:Car):Unit= {
    println("Debug:calling unapply ...")
//    Some((c.brand, c.price))
  }}