package channels.examples

import scala.concurrent.Future
import play.api.libs.ws.WS
import backend.SocketStream
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json._
import channels.Channel
import play.api.libs.functional.syntax._

case class WeatherForecast(dayName: String, weatherType: String, image: String)

class BBCWeatherChannel(key: String) extends Channel[Future[Unit]] {

  implicit val forecastReads: Reads[WeatherForecast] = (
    (JsPath \ "dayName").read[String] ~
    (JsPath \ "weatherType").read[String] ~
    (JsPath \ "weatherSymbol" \ "webMedium").read[String]
    )(WeatherForecast.apply _)

  implicit val forecastWrites = Json.writes[WeatherForecast]

  private val weatherAPI = "http://weather-api-proxy.cloud.bbc.co.uk/weather/feeds/en/2655981/3hourlyforecast.json"

  def run(): Future[Unit] = {

    WS.url(weatherAPI).get().map { response =>
      val json = Json.parse(response.body)
      val forecasts = (json \ "forecastContent" \ "forecasts").as[Seq[WeatherForecast]]

      forecasts.headOption match {
        case Some(forecast) => SocketStream.push(key, Json.stringify(Json.toJson(forecast)))
        case None => ()
      }
    }
  }
}
