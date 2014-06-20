package widgets.examples

// A simple example for an RSS widget

import backend.SocketChannel
import scala.xml._
import widgets.Widget

class BBCNewsWidget(key: String) extends Widget {

  private val url = "http://feeds.bbci.co.uk/news/rss.xml"

  def parseNode(node: Node): String = {
    (node \\ "title").text
  }

  def run() = {
    val root: Elem = XML.load(url)
    val stories: List[String] = (root \\ "item").map(parseNode).toList
    val story: String = stories.head

    SocketChannel.push(key, story)
  }
}
