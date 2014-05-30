package widgets

import backend.SocketChannel
import scala.util.Random.nextInt

/**
 * A very basic widget that pushes a random number to the socket channel
 * every time "run" is called
 *
 */
class SimpleWidget(key: String) extends Widget {

  def run() = {
    SocketChannel.push(key, nextInt(1000).toString)
  }
}
