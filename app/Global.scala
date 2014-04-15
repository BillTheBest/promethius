import controllers.Application
import play.api._

import actors._
import play.api.Application
import widgets.{LoggingWidget, PollingWidget}

object Global extends GlobalSettings {

  override def onStart(app: Application) {

    Logger.info("Application has started")

    val w = new PollingWidget("http://time.jsontest.com")
    Scheduler.start(w.run)

    val u = new LoggingWidget()
    Scheduler.start(u.run)
  }

  override def onStop(app: Application) {
    Logger.info("Application shutdown...")
  }

}