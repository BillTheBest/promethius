package widgets

import scala.concurrent.Future
import play.api.libs.ws.WS
import backend.SocketChannel
import play.api.libs.concurrent.Execution.Implicits._

class PollingWidget(key: String, url: String) extends Widget {

  def run(): Future[Unit] = {
    WS.url(url).get().map { response =>
      SocketChannel.push(key, response.body)
    }
  }
}

