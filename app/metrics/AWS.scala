package metrics

import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient
import scala.collection.JavaConversions._

final class AWS {

  lazy val awsClient: AmazonCloudWatchClient = {
    def client = new AmazonCloudWatchClient
    client.setEndpoint("monitoring.eu-west-1.amazonaws.com")
    client
  }

  def listMetrics() {
    val metrics = awsClient.listMetrics().getMetrics
    for (metric ← metrics) {
      println(metric)
    }
  }
}

object AWSCloudwatch {

  def apply() = new AWS()

}
