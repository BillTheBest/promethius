package controllers

import play.api.mvc._
import play.api.libs.iteratee.{Enumerator, Concurrent, Iteratee}
import play.api.libs.iteratee.Concurrent.Channel
import play.api.libs.concurrent.Execution.Implicits._

import backend.SocketChannel

object Application extends Controller {

  val (out: Enumerator[String], channel: Channel[String]) = Concurrent.broadcast[String]

  private val in = Iteratee.foreach[String] { msg => channel.push(msg) }

  val webSocket = (in, out)

  def index = Action { implicit request =>
    Ok(views.html.index(""))
  }

  // The web socket
  def socket: WebSocket[String] = SocketChannel.get()
}