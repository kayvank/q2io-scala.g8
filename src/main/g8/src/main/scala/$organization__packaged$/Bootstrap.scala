import api.StatusApi
import com.typesafe.scalalogging.LazyLogging
import kamon.Kamon
import org.http4s.server.{Server, ServerApp}
import org.http4s.server.blaze.BlazeBuilder
import utils.Global._
import repository.Ds._
import scalaz._
import Scalaz._
import scalaz.concurrent.Task


object Bootstrap extends ServerApp with LazyLogging {

  import utils.ApplicativeTask._
  //TODO uncomment once databse is provisioned
  //println(s"connecting to jdbcUrl = $jdbcUrl as jdbcUser=${jdbcUser}.  dbUp= ${connectionStatus}")

  case class ProgramStatus(s: Server, u: Unit)

  Kamon.start()

  def server(args: List[String]): Task[Server] = {

    import utils.CustomExecutor._

    val serverTask = BlazeBuilder.bindHttp(
      port = cfgV.getInt("http.port"),
      host = "0.0.0.0")
      .mountService(StatusApi.service, "/status").start

    T.apply2(
      Task.fork(serverTask)(ec),
      Task.fork(Task.delay(println("hellow world from Applicatives!")))(customExecutor))(ProgramStatus(_, _)
    ) map (_.s)
  }
}
