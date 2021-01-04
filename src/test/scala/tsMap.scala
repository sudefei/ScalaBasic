import com.alibaba.fastjson.{JSON, JSONArray, JSONObject}
import com.sdf.config.GetConfig
import com.sdf.etl.HttpUtils
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test
import wsd.config.GetConfigff
import wsd.opdb.{OpdbInsert, OpdbQueries, Queries, Query}
import wsd.util.ExcelReadWriter

import scala.collection.JavaConversions._
import scala.collection.mutable
import scala.collection.mutable.Map

class tsMap {

    val prop=GetConfigff.getProperties

    @Test
    def ts1= {
        val conf = new SparkConf().setAppName("SparkReadOpdb")
            .setMaster("local[2]")
        val sc = SparkContext.getOrCreate(conf)
        val lines = sc.textFile("src/main/resources/tsdata").map((_,1))
        val l = System.currentTimeMillis()
        val value = lines.sortByKey()
        println(System.currentTimeMillis() - l)
//        value.saveAsTextFile("src/main/resources/rs.txt")
        value.saveAsObjectFile("src/main/resources/rs")
        sc.stop()
    }

    @Test
    def ts2: Unit ={
//        val map = Map("start" -> "2020/05/13-00:00:00","end"->"2020/05/13-00:00:00")
        val str1 =OpdbQueries("2020/05/12-00:00:00","2020/05/13-00:00:00",Map("samplingmode"->"Interpolated"),
            "u005au0048u002du0048u0049u0053u002eu5377u70dfu673au002eu004du0035u002eu0034u0023u002eu673au5668u901fu5ea6").toString
        println(str1)
        val str2=HttpUtils.opdbPost(str1)
        println(str2)
        val jso = JSON.parseArray(str2)
//        println(jso.getJSONObject(0).getJSONObject("dps").size())
//        jso.getJSONObject(0).getJSONObject("dps").toMap

    }

    @Test
    def ts3: Unit = {
        val li =List(Query("u005au0048u002du0048u0049u0053u002eu5377u70dfu673au002eu004du0035u002eu0034u0023u002eu673au5668u901fu5ea6",
            Map("samplingmode"->"Interpolated")).getQuery,Query("u005au0048u002du0048u0049u0053u002eu5377u70dfu673au002eu004du0035u002eu0034u0023u002eu673au5668u901fu5ea6",
            Map("samplingmode"->"Interpolated")).getQuery,Query("u005au0048u002du0048u0049u0053u002eu5377u70dfu673au002eu004du0035u002eu0036u0023u002eu673au5668u901fu5ea6",
            Map("samplingmode"->"Interpolated")).getQuery)

        println(li)
//        println(Queries("2020/05/21-00:00:00", "2020/05/22-00:00:00", li).toString)
        var jsa = new JSONArray()
        jsa.fluentAddAll(li)

        println(jsa.toString())
        println(HttpUtils.post(prop.getProperty("OPDBURL"), Queries("2020/05/21-00:00:00", "2020/05/22-00:00:00", li).toString))
    }

    @Test
    def ts4: Unit ={
//        val dbj = new dbJdbc()
        val tags = prop.getProperty("tagname")
//        val tags = prop.getProperty("tag2name")
        val titles= Array("tag", "time", "value");
        val workbook = ExcelReadWriter.createTagExcel("Tag_Humidity",titles)
        var i:Int=0;
      tags.split(",").foreach(tag=>{
            print(tag+":")
            val str = HttpUtils.post(prop.getProperty("OPDBURL"), OpdbQueries("2020/07/06-00:00:00", "2020/07/11-00:00:00",
                  mutable.Map("driver_name" -> "humidity", "topic_name" -> "pd_energy"),
//                mutable.Map("driver_name" -> "temperature", "topic_name" -> "pd_energy"),
                tag).toString)
            var js :JSONObject=null;
            if (str.length>=2){
                js = JSON.parseArray(str).getJSONObject(0).getJSONObject("dps")
            }
//            println(js)
          val tagSheet:XSSFSheet = workbook.getSheet("Tag_Humidity")
            js.keySet().foreach(key=>{
                  i=i+1;
              ExcelReadWriter.writerTag(tagSheet,tag,key.toInt,js.getDoubleValue(key),i)
//                dbj.insertTmptag(tag,key.toInt,js.getDoubleValue(key))
            })

        })
      ExcelReadWriter.writerToExcel(workbook)
//        dbJdbc.close()

    }

    @Test
    def ts5: Unit ={
        println(GetConfig.getProperties.getProperty("OPDBURL"))
    }

    @Test
    def ts6:Unit={
      var jsonArray:JSONArray =new JSONArray()
      // 获取一天内的所有ac_x 的值
      val json = OpdbInsert("ZDCG0002", "1596660630", Map("dimension" -> "ac_x"), "-15").ReturnJson
      val json_01 = OpdbInsert("ZDCG0002", "1596660631", Map("dimension" -> "ac_x"), "-14").ReturnJson
      jsonArray.add(json)
      jsonArray.add(json_01)
      val str = HttpUtils.opdbTestPost(jsonArray.toString)
      print(str)
    }
}
