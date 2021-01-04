package sdf.config

import java.util.Properties

object ProjectConf {
    val prop=new Properties()
    prop.setProperty("OPDBURL", "http://10.10.185.129:4244/api/query")
    //mysql地址及账号
    prop.setProperty("mysqlDriver", "com.mysql.jdbc.Driver")
    prop.setProperty("mysqlUrl", "jdbc:mysql://master.cm.com:3306/JBSPC?useUnicode=true&characterEncoding=utf8")
    prop.setProperty("mysqlUser", "root")
    prop.setProperty("mysqlPass", "admin@123")
    //oracle地址及账号
    prop.setProperty("oracleDriver","oracle.jdbc.OracleDriver")
    prop.setProperty("oracleUrl","jdbc:oracle:thin:@//10.10.184.71:1521/mespdb")
    prop.setProperty("oracleUser","c##occ")
    prop.setProperty("oraclePass","Pass00#rd")
    //kakfa测试集群地址
    prop.setProperty("bootstrap.servers.test", "work3.cdh.com:9092,work4.cdh.com:9092,work5.cdh.com:9092,work9.cdh.com:9092,work10.cdh.com:9092")
    //kafka烟厂集群地址
    prop.setProperty("bootstrap.servers.dev", "node1.cm.com:9092,node2.cm.com:9092,node3.cm.com:9092,node4.cm.com:9092")

}
