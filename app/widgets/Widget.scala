package widgets

import play.api.libs.iteratee.Concurrent.Channel
import play.api.libs.ws.WS
import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.Future

trait Widget {

  def run(channel: Channel[String]): Any

  def fetchUrl(url: String, channel: Channel[String]): Future[Unit] = {
    WS.url(url).get().map { response =>
      channel.push(response.body)
    }
  }
}
