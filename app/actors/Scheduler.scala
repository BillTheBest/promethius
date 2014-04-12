package actors

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

import play.api.libs.ws._

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.duration._

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

  // Every 5 seconds query JSON from the API and push it to our websocket !
  def sendMessage() = {
    WS.url("http://time.jsontest.com").get().map { response =>
      scheduleActor ! Message(response.body)
    }
  }

  def start() = {
    system.scheduler.schedule(0 seconds, 5 seconds)(sendMessage())
  }

}
