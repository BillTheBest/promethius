
import play.api._
import actors._
import play.api.Application
import widgets._
import widgets.examples._
import metrics.AWSCloudwatch

object Global extends GlobalSettings {

  def runWidgets() {

    val a = new JsonWidget("time")
    Scheduler.start(a.run, 1)

    val b = new SimpleWidget("random-number")
    Scheduler.start(b.run, 100)

    val c = new BBCNewsWidget("bbc-news")
    Scheduler.start(c.run, 5)


    // This widget sits IDLE waiting for incoming HTTP post requests and so does not need
    // a scheduler
    new PostWidget("post-metric")
  }

  override def onStart(app: Application) {
    Logger.info("Application has started")
    runWidgets()
  }

  override def onStop(app: Application) {
    Logger.info("Application shutdown...")
  }
}