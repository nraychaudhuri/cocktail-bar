package common

import play.api.libs.iteratee.Iteratee
import play.api.mvc.BodyParser
import play.core.j.JavaParsers
import play.mvc.Http.RequestBody
import scala.beans.BeanProperty
import scala.concurrent.Future
import scala.concurrent.duration._
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.concurrent.Promise

final class LogEvent(@BeanProperty val level: String, @BeanProperty val message: String)

object Utils {

  val bodyParser: BodyParser[RequestBody] = BodyParser { request =>
    Iteratee.foldM[Array[Byte], Int](0)(parseOrders).map(makeJavaRequestBody).map(Right(_))
  }


  private def makeJavaRequestBody(count: Int) = {
    new RequestBody {
      override def asText(): String = count.toString
    }
  }

  val logPattern = """\[(INFO|DEBUG)\] - ([a-z ]*)""".r

  def toEvent(line: String): LogEvent = {
    logPattern.findFirstIn(line) match {
      case Some(logPattern(level, message)) =>
        if(level == "INFO") new LogEvent("info", message) else new LogEvent("debug", message)
      case _ =>
        new LogEvent("other", line)
    }
  }

  private def parseOrders(count: Int, bytes: Array[Byte]): Future[Int] = {
    println(">> got chunk")
    //do whatever needs to be done to place an order
    Promise.timeout(count + 1, 200 milliseconds)
  }

}
