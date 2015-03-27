package channels.examples

// A simple example for an RSS widget

import backend.SocketStream
import scala.collection.immutable.Seq
import scala.xml._
import channels.Channel

class BBCNewsChannel(key: String) extends Channel[Unit] {

  private val url = "http://feeds.bbci.co.uk/news/rss.xml"

  def parseNode(node: Node): String =
    (node \\ "title").text

  def getStories: Seq[String] = (XML.load(url) \\ "item").map(parseNode)

  def run(): Unit = {
    SocketStream.push(key, getStories.headOption.getOrElse(""))
  }
}
