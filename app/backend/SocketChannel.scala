package backend

import play.api.libs.iteratee.{Iteratee, Concurrent, Enumerator}
import play.api.libs.iteratee.Concurrent.Channel
import play.api.mvc.WebSocket
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json._

import models._

object SocketChannel {

  val (out: Enumerator[String], channel: Channel[String]) = Concurrent.broadcast[String]

  def asJsonString(m: Message) = {
    Json.stringify(Json.toJson(m))
  }

  private val in = Iteratee.foreach[String] { msg => channel.push(msg) }

  def push(key: String, message: String) = {
    val m = Message(key, message)
    channel.push(asJsonString(m))
  }

  def get(): WebSocket[String] = {
    WebSocket.using[String] { _ =>
      (in, out)
    }
  }
}
