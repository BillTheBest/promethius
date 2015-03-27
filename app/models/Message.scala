package models

import play.api.libs.json.Json

case class Message(key: String, value: String)

object Message {

  implicit val messageFormat = Json.format[Message]
}
