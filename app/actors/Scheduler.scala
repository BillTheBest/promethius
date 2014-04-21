package actors

import akka.actor.ActorSystem
import scala.concurrent.duration._
import backend.Settings
import play.api.libs.concurrent.Execution.Implicits._

object Scheduler {

  val system = ActorSystem("scheduler")

  def start(f: () => Any, frequency: Int = Settings.pollFrequency) = {
    println("Starting scheduler")
    system.scheduler.schedule(0 seconds, frequency seconds) { f() }
  }
}
