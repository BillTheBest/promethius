package backend

import play.api.libs.iteratee.{Iteratee, Concurrent, Enumerator}
import play.api.libs.iteratee.Concurrent.Channel
import play.api.mvc.WebSocket
import play.api.libs.concurrent.Execution.Implicits._

import play.api.libs.json._

case class Message(key: String, value: String)

object Message {
  implicit val messageFmt = Json.format[Message]
}

object SocketChannel {

  val (out: Enumerator[String], channel: Channel[String]) = Concurrent.broadcast[String]

  private val in = Iteratee.foreach[String] { msg => channel.push(msg) }

  def push(key: String, message: String) = {
    val m = Json.toJson(Message(key, message))
    channel.push(Json.stringify(m))
  }

  def get(): WebSocket[String] = {
    WebSocket.using[String] { _ =>
      (in, out)
    }
  }
}
