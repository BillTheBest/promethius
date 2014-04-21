package widgets

import backend.SocketChannel
import scala.util.Random.nextInt

 class SimpleWidget(key: String) extends Widget {

  def run() = {
    SocketChannel.push(key, nextInt(1000).toString)
  }
}
