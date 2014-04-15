package widgets

import scala.concurrent.Future
import play.api.libs.iteratee.Concurrent.Channel

class PollingWidget extends Widget {

  def run(channel: Channel[String]): Future[Unit] = {
    fetchUrl("http://time.jsontest.com", channel)
  }
}

