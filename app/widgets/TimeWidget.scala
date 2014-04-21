package widgets

import scala.concurrent.Future
import play.api.libs.ws.WS
import backend.SocketChannel
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json._

class TimeWidget(key: String) extends Widget {

  private val url = "http://time.jsontest.com"

  def run(): Future[Unit] = {

    WS.url(url).get().map { response =>
      val json        = Json.parse(response.body)
      val currentTime = (json \ "time").asOpt[String]
      SocketChannel.push(key, currentTime.getOrElse(""))
    }
  }
}
