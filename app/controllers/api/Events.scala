package controllers.api

import play.api.mvc._
import play.api.libs.json._
import uk.co.bbc.models.Message
import backend.SocketChannel

// Events API for pushing data to the dashboard

object Events extends Controller {

  /**
   * Handler for the JSON post request
   *
   * Will push a message onto the Socket Channel
   *
   */
  def handleRequest(json: JsValue) = {
    val message: JsResult[Message] = Json.fromJson[Message](json)
    message.map { result =>
        SocketChannel.push(result.key,result.value)
        Ok("Event accepted").as("application/json")
    }.getOrElse { BadRequest("Invalid message format") }
  }

  /**
   * You can make arbitrary HTTP post requests to this endpoint
   *
   * It requires a content type of application/json and a simple key value like JSON structure
   *
   * POST /api/metrics -d '{"key":"post-metric","value":"123"'}
   *
   * If a widget matches the key supplied it will be updated in real time on the front end
   *
   */
  def create: Action[AnyContent] = Action { request =>
    lazy val json = request.body.asJson
    json.map(handleRequest).getOrElse { BadRequest("Expecting Json data") }
  }
}
