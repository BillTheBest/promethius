package widgets.examples

// A simple example for an RSS widget

import backend.SocketStream
import scala.xml._
import widgets.Widget

class BBCNewsWidget(key: String) extends Widget {

  private val url = "http://feeds.bbci.co.uk/news/rss.xml"

  def parseNode(node: Node): String = {
    (node \\ "title").text
  }

  def run(): Unit = {
    val stories: List[String] = (XML.load(url) \\ "item").map(parseNode).toList
    val story: String = stories.head

    SocketStream.push(key, story)
  }
}
