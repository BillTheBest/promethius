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

  private val url = "https://vacancy.io/api/v1/stats"

  def handleResponse(users: Int,
                     companies: Int,
                     lastUserSignUp: String,
                     lastCompanySignUp: String): Unit = {
    SocketChannel.push("users", users.toString)
    SocketChannel.push("companies", companies.toString)
    SocketChannel.push("last-user-sign-up", lastUserSignUp)
    SocketChannel.push("last-company-sign-up", lastCompanySignUp)
  }

  def run(): Future[Unit] = {
    WS.url(url).get().map { response ⇒
      val json = Json.parse(response.body)
      val users = (json \ "users").as[Int]
      val companies = (json \ "companies").as[Int]
      val lastUserSignUp = (json \ "last_user_sign_up").as[String]
      val lastCompanySignUp = (json \ "last_company_sign_up").as[String]
      // Update the dashboard
      handleResponse(users, companies, lastUserSignUp, lastCompanySignUp)
    }
  }
}
