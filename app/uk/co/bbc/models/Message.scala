package uk.co.bbc.models

import play.api.libs.json.Json

case class Message(
  key: String,
  value: String)

object Message {

  implicit val messageFmt = Json.format[Message]
}