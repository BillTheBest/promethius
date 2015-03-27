package controllers

import play.api.mvc._
import play.api.libs.iteratee.{ Enumerator, Concurrent, Iteratee }
import play.api.libs.iteratee.Concurrent.Channel
import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.Future
import backend.SocketStream

object Application extends Controller {

  def socket: WebSocket[String] = SocketStream.get()

  def index = Action.async {
    Future {
      Ok(views.html.index())
    }
  }
}
