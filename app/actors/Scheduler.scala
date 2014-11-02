package actors

import akka.actor.ActorSystem
import scala.concurrent.duration._
import backend.Settings
import play.api.libs.concurrent.Execution.Implicits._

object Scheduler {

  val system = ActorSystem("scheduler")

  /** Start the scheduler
    */
  def start(f: () ⇒ Any, frequency: Int = Settings.pollFrequency) = {
    system.scheduler.schedule(0 seconds, frequency seconds) { f() }
  }
}
