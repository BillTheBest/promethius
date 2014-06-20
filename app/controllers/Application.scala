package controllers

import play.api.mvc._
import play.api.libs.iteratee.{ Enumerator, Concurrent, Iteratee }
import play.api.libs.iteratee.Concurrent.Channel
import play.api.libs.concurrent.Execution.Implicits._

import backend.SocketChannel

object Application extends Controller {

  def index = Action { implicit request ⇒
    Ok(views.html.index(""))
  }

  // The web socket
  def socket: WebSocket[String] = SocketChannel.get()
}
