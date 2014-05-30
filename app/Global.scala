
import play.api._
import actors._
import play.api.Application
import widgets._

object Global extends GlobalSettings {

  def runWidgets() {

    val a = new JsonWidget("time")
    Scheduler.start(a.run, 1)

    val b = new SimpleWidget("random-number")
    Scheduler.start(b.run, 100)

    val c = new OnlineWidget("github-status", "http://github.com")
    Scheduler.start(c.run, 5)

    val d = new BBCNewsWidget("bbc-news")
    Scheduler.start(d.run, 5)

    val e = new LineChartWidget("chart-metrics")
    Scheduler.start(e.run, 5)

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