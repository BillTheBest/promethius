package widgets.examples

import backend.SocketStream
import scala.util.Random.nextInt
import widgets.Widget

/** A very basic widget that pushes a random number to the socket channel
  * every time "run" is called
  *
  */
class SimpleWidget(key: String) extends Widget {

  def run() = {
    SocketStream.push(key, nextInt(1000).toString)
  }
}
