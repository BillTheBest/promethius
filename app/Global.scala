
import play.api._
import actors._
import play.api.Application
import widgets._

object Global extends GlobalSettings {

  def runWidgets() {

    val a = new TimeWidget("time")
    Scheduler.start(a.run, 1)

    val b = new SimpleWidget("random-number")
    Scheduler.start(b.run, 2)

    val c = new OnlineWidget("github-status", "http://github.com")
    Scheduler.start(c.run, 5)
  }

  override def onStart(app: Application) {
    Logger.info("Application has started")
    runWidgets()
  }

  override def onStop(app: Application) {
    Logger.info("Application shutdown...")
  }
}