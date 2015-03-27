package controllers

import backend.SocketStream
import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc._
import scala.concurrent.Future

object Application extends Controller {

  def socket: WebSocket[String] = SocketStream.get()

  def index = Action.async {
    Future {
      Ok(views.html.index())
    }
  }
}
