import controllers.Application
import play.api._

import actors._
import play.api.Application
import widgets.{LoggingWidget, PollingWidget}

object Global extends GlobalSettings {

  override def onStart(app: Application) {

    Logger.info("Application has started")

    val w = new PollingWidget()
    val u = new LoggingWidget()

    Scheduler.start(Application.channel, w.run)
    Scheduler.start(Application.channel, u.run)
  }

  override def onStop(app: Application) {
    Logger.info("Application shutdown...")
  }

}