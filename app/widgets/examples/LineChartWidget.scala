package widgets.examples

import backend.SocketChannel
import play.api.libs.json.Json
import widgets.Widget

class LineChartWidget(key: String) extends Widget {

  def run() = {

    val metrics = Map("key1" -> Seq(1.0, 2.0, 4.0))

    val metricJson = Json.stringify(Json.toJson(metrics))

    SocketChannel.push(key, metricJson)
  }
}
