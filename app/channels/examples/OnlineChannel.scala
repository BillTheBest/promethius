package channels.examples

// A widget that checks if a website is up

import backend.SocketStream
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.ws.WS
import channels.Channel

class OnlineChannel(key: String, url: String) extends Channel[Unit] {

  val f: (String) => Unit = { x: String ⇒ SocketStream.push(key, x) }

  def run() = {
    WS.url(url).get map { response =>
      response.status match {
        case 200 ⇒ f("UP")
        case _   ⇒ f("DOWN")
      }
    }
  }
}
