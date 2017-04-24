package repository

import doobie.imports._
import scalaz._
import Scalaz._
import scalaz.concurrent.Task
import utils.Global._
import doobie.hikari.hikaritransactor._

object Ds {
  val jdbcUrl = cfgVevo.getString("db.jdbc.url")
  val jdbcUser = cfgVevo.getString("db.jdbc.user")
  val jdbcPass = cfgVevo.getString("db.jdbc.password")
  val jdbcDriver = cfgVevo.getString("db.jdbc.driver")

  val connectionPoolThreads = cfgVevo.getInt("db.connection.pool.threads")
  implicit val hxa: HikariTransactor[Task] =
    HikariTransactor[Task](
      "org.postgresql.Driver",
      jdbcUrl,
      jdbcUser,
      jdbcPass) unsafePerformSync

  val _= (hxa.configure(hx =>{
    hx.setAutoCommit(false)
    hx.setPoolName("qos_service")
    hx.setMaximumPoolSize(connectionPoolThreads)
    Task.delay(hx)
  })) unsafePerformSync

  def connectionStatus: Boolean = {
    val program3 = sql"select 42".query[Int].unique
    (program3.transact(hxa).unsafePerformSync == 42 )
  }
}
