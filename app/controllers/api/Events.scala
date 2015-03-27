package controllers.api

import models.Message
import play.api.mvc._
import backend.SocketStream
import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext

// Events API for pushing data to the dashboard

object Events extends Controller {

  /** You can make arbitrary HTTP post requests to this endpoint
    *
    * It requires a content type of application/json and a simple key value like JSON structure
    *
    * curl -iX POST http://localhost:9000/api/events -d '{"key": "post-metric","value": "123"}' -H "Content-Type: application/json"
    *
    * If a widget matches the key supplied it will be updated in real time on the front end
    *
    */
  def create = Action.async(parse.json) { request ⇒
    request.body.validate[Message].fold(
      errors ⇒ {
        Future(
          BadRequest("Invalid message")
        )
      },
      message ⇒ {
        Future {
          SocketStream.push(message.key, message.value)
          Ok("Event accepted").as("application/json")
        }
      }
    )
  }
}
