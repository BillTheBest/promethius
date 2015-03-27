package models

import models.Message
import org.junit.runner.RunWith
import org.specs2.mutable._
import org.specs2.runner._
import play.api.libs.json._

@RunWith(classOf[JUnitRunner])
class MessageSpec extends Specification {

  val messageFixture = Message("foo", "bar")

  "A message case class" should {
    "get converted to and from JSON correctly" in {
      val expected = """{"key":"foo","value":"bar"}"""
      Json.stringify(Json.toJson(messageFixture)) must equalTo(expected)
    }
  }
}


