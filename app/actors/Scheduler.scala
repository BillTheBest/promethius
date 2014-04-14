package actors

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

import play.api.libs.ws._

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.duration._
import scala.concurrent.Future
import play.api.libs.iteratee.Concurrent.Channel

case class Message(msg: String)

class Scheduler extends Actor {

  def receive = {
    case Message(v) => println(v)
    case _          => println("Bad message")
  }
}

object ScheduleRunner {

  val system = ActorSystem("scheduler")

  val scheduleActor = system.actorOf(Props[Scheduler], name = "scheduler")

  def sendMessage(channel: Channel[String]): Future[Unit] = {
    WS.url("http://time.jsontest.com").get().map { response =>
      channel.push(response.body)
    }
  }

  def start(channel: Channel[String]) = {
    system.scheduler.schedule(0 seconds, 5 seconds) { sendMessage(channel) }
  }
}
