package widgets.examples

// A widget that checks if a website is up

import scala.concurrent.Future
import play.api.libs.ws.{ Response, WS }
import backend.SocketChannel
import play.api.libs.concurrent.Execution.Implicits._
import widgets.Widget

class OnlineWidget(key: String, url: String) extends Widget {

  def run() = {
    val response: Future[Response] = WS.url(url).get()
    val f: (String) ⇒ Unit = { x: String ⇒ SocketChannel.push(key, x) }
    response.map { r ⇒
      r.status match {
        case 200 ⇒ f("UP")
        case _   ⇒ f("DOWN")
      }
    }
  }
}
