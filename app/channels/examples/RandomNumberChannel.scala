package channels.examples

import backend.SocketStream
import scala.util.Random.nextInt
import channels.Channel

class RandomNumberChannel(key: String) extends Channel[Unit] {

  def run(): Unit = {
    SocketStream.push(key, nextInt(1000).toString)
  }
}
