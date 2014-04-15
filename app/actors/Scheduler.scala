package actors

import akka.actor.ActorSystem
import scala.concurrent.duration._
import backend.Settings
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.iteratee.Concurrent.Channel

object Scheduler {

  val system = ActorSystem("scheduler")

  def start(channel: Channel[String], f: Channel[String] => Any) = {
    println("Starting scheduler")
    system.scheduler.schedule(0 seconds, Settings.pollFrequency seconds) { f(channel) }
  }
}
