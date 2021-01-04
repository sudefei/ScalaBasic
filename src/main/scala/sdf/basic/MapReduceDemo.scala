package sdf.basic

object MapReduceDemo {

  def main(args: Array[String]): Unit = {
    val list=List(1,2,3,4,5);
    list.map(x=>(x,1))
    list.map(x=>x+1)
    // reduce 默认是 左规约
    //从左到右规约，左边的数作为 a
    //从右到左规约，右边的数作为 b
    list.reduce(_-_)          // -13
    list.reduceLeft(_-_)      // -13
    list.reduceRight(_-_)     // 3
    // fold 也是规约，但是 fold 可以赋初始值

    list.reduce(_*_)           // 120
    list.fold(1){_*_}         //  120
    list.fold(3){_*_}         //  360

    list.foldLeft(10){_-_}  //计算顺序 (((((10-1)-2)-3)-4)-5) = -5
    list.foldRight(10){_-_}  //计算顺序 (1-(2-(3-(4-(5-10))))) = -7
  }

}
