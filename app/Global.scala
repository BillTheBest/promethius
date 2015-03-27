
import play.api._
import play.api.Application
import channels.examples._
import actors.Scheduler

object Global extends GlobalSettings {

  val newsChannel         = new BBCNewsChannel("news")

  val randomNumberChannel = new RandomNumberChannel("random")

  def tick(): Unit = {
    newsChannel.run()
    randomNumberChannel.run()
  }

  def startChannelScheduler() = Scheduler.start(tick, 1)

  override def onStart(app: Application) {
    startChannelScheduler()
  }
}
