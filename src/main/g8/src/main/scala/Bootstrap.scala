
import api.StatusApi
import com.typesafe.scalalogging.LazyLogging
import org.http4s.server.{Server, ServerApp}
import org.http4s.server.blaze.BlazeBuilder
import utils._
import Global._
import repository.Ds._
import scalaz._, Scalaz._
import scalaz.concurrent.Task

object Bootstrap extends ServerApp with LazyLogging {

  case class ProgramStatus(s: Server, u: Unit)

  def server(args: List[String]): Task[Server] = {

    import CustomExecutor._
    import ApplicativeTask._

    val serverTask = BlazeBuilder.bindHttp(
      port = cfgVevo.getInt("http.port"),
      host = "0.0.0.0")
      .mountService(StatusApi.service, "/status").start

    T.apply2(
      Task.fork(serverTask)(ec),
      Task.fork(Task.delay(println("hellow world from Applicatives!")))(customExecutor))(ProgramStatus(_, _)
    ) map (_.s)
  }
}
