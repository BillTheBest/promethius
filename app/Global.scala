import play.api._

import actors._

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    Logger.info("Application has started")

    ScheduleRunner.start()
  }

  override def onStop(app: Application) {
    Logger.info("Application shutdown...")
  }

}