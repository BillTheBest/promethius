
import play.api._
import actors._
import play.api.Application
import widgets._

object Global extends GlobalSettings {

  def runWidgets() {

    val w = new PollingWidget("http://time.jsontest.com")
    Scheduler.start(w.run)

    val u = new PollingWidget("http://www.frequency.io/metrics")
    Scheduler.start(u.run)
  }

  override def onStart(app: Application) {
    Logger.info("Application has started")
    runWidgets()
  }

  override def onStop(app: Application) {
    Logger.info("Application shutdown...")
  }
}