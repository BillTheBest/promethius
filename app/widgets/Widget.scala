package widgets

import play.api.libs.iteratee.Concurrent.Channel
import play.api.libs.ws.WS
import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.Future
import backend.SocketChannel

trait Widget {

  def run(): Any

  def fetchUrl(url: String): Future[Unit] = {
    WS.url(url).get().map { response =>
      SocketChannel.push(response.body)
    }
  }
}
