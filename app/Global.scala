
import play.api._
import actors._
import play.api.Application
import widgets._
import widgets.examples._
import metrics.AWSCloudwatch

object Global extends GlobalSettings {

  def runWidgets() {

    val a = new JsonWidget("vacancy")
    Scheduler.start(a.run, 10)
  }

  override def onStart(app: Application) {
    Logger.info("Application has started")
    runWidgets()
  }

  override def onStop(app: Application) {
    Logger.info("Application shutdown...")
  }
}
