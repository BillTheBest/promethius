package backend

import play.api.libs.iteratee.{Iteratee, Concurrent, Enumerator}
import play.api.libs.iteratee.Concurrent.Channel
import play.api.mvc.WebSocket
import play.api.libs.concurrent.Execution.Implicits._

object SocketChannel {

  val (out: Enumerator[String], channel: Channel[String]) = Concurrent.broadcast[String]

  private val in = Iteratee.foreach[String] { msg => channel.push(msg) }

  def push(message: String) = {
    channel.push(message)
  }

  def get(): WebSocket[String] = {
    WebSocket.using[String] { _ =>
      (in, out)
    }
  }
}
