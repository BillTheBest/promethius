package controllers

import play.api.mvc._
import play.api.libs.iteratee.{Concurrent, Iteratee}
import play.api.libs.concurrent.Execution.Implicits._

case class Start(out: Concurrent.Channel[String])

object Application extends Controller {

  def index = Action { implicit request =>
    Ok(views.html.index(""))
  }

  // The web socket
  def socket = WebSocket.using[String] { _ =>
    val (out,channel) = Concurrent.broadcast[String]
    val in = Iteratee.foreach[String] {
      msg => channel.push(msg)
    }
    (in,out)
  }
}