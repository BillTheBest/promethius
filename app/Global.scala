
import play.api._
import play.api.Application
import widgets.examples._
import actors.Scheduler

object Global extends GlobalSettings {

  /** This is the main entry point for kicking off any workers
    *
    * All widgets must be attached to a Scheduler for the dashboard to update
    */
  def runWidgets() {

    val news = new BBCNewsWidget("news")
    Scheduler.start(news.run, 10)
  }

  override def onStart(app: Application) {
    Logger.info("Application has started")
    runWidgets()
  }

  override def onStop(app: Application) {
    Logger.info("Application shutdown...")
  }
}
