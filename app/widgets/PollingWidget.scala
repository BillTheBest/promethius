package widgets

import scala.concurrent.Future

class PollingWidget(url: String) extends Widget {

  def run(): Future[Unit] = fetchUrl(url)

}

