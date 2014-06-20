package widgets.examples

import scala.concurrent.Future
import play.api.libs.ws.WS
import backend.SocketChannel
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json._
import widgets.Widget

/** This is a simple widget that fetches JSON data from an external endpoint
  * every time the run method is called
  *
  * It parses the time value out of the JSON response and pushes it to the socket channel
  *
  */
class JsonWidget(key: String) extends Widget {

  private val url = "http://time.jsontest.com"

  def run(): Future[Unit] = {

    WS.url(url).get().map { response ⇒
      val json = Json.parse(response.body)
      val currentTime = (json \ "time").asOpt[String]
      SocketChannel.push(key, currentTime.getOrElse(""))
    }
  }
}
