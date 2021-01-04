package sdf.jdbc

import java.sql.{Connection, DriverManager, PreparedStatement, SQLException}

import org.slf4j.LoggerFactory

object PhoenixJdbcUtils {

  private val logger = LoggerFactory.getLogger("")

  def truncateTable(table: String) {
    try {
      truncateTableEx(DriverManager.getConnection("jdbc:phoenix:node1.cm.com,node2.cm.com,node3.cm.com:2181"), table)
    } catch {
      case e:Exception => logger.error(e.toString)
    }
  }

  def deleteStatement(conn: Connection, table: String)
  : PreparedStatement = {
    val sql = s"DELETE FROM $table"
    conn.prepareStatement(sql)
  }

  def truncateTableEx(conn: Connection, table: String): Unit = {
    try {
      val stmt = deleteStatement(conn, table)
      try {
        stmt.executeUpdate()
      } finally {
        stmt.close()
      }
      conn.commit()
    } catch {
      case e: SQLException =>
        val cause = e.getNextException
        if (cause != null && e.getCause != cause) {
          if (e.getCause == null) {
            e.initCause(cause)
          } else {
            e.addSuppressed(cause)
          }
        }
        throw e
    } finally {
      conn.close()
    }
  }
}
