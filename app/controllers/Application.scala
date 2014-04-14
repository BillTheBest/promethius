package controllers

import play.api.mvc._
import play.api.libs.iteratee.{Enumerator, Concurrent, Iteratee}
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.iteratee.Concurrent.Channel
import actors.ScheduleRunner

case class Start(out: Concurrent.Channel[String])

object Application extends Controller {

  val eventQueue = List()

  def index = Action { implicit request =>
    Ok(views.html.index(""))
  }

  def sendTo(channel: Channel[String], msg: String) = {
    channel.push(msg)
  }

  // The web socket
  def socket = WebSocket.using[String] { _ =>

    val (out: Enumerator[String], channel: Channel[String]) = Concurrent.broadcast[String]
    val in = Iteratee.foreach[String] { msg => channel.push(msg) }

    ScheduleRunner.start(channel)

    (in,out)
  }
}