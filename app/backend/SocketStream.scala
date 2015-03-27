package backend

import models.Message
import play.api.libs.iteratee.{ Iteratee, Concurrent, Enumerator }
import play.api.libs.iteratee.Concurrent.Channel
import play.api.mvc.WebSocket
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json._

object SocketStream {

  private val (out: Enumerator[String], channel: Channel[String]) =
    Concurrent.broadcast[String]

  def asJsonString(m: Message): String = {
    Json.stringify(Json.toJson(m))
  }

  private val in = Iteratee.foreach[String] { msg ⇒ channel.push(msg) }

  /** Push a message onto the stream
    */
  def push(key: String, message: String): Unit = {
    val m = Message(key, message)
    channel.push(asJsonString(m))
  }

  /** Fetch from the stream
    */
  def get(): WebSocket[String] = {
    WebSocket.using[String] { _ ⇒
      (in, out)
    }
  }
}
