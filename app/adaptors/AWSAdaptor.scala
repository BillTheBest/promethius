package adaptors

import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient
import scala.collection.JavaConversions._
import com.amazonaws.regions._

final class AWSAdaptor {

  lazy val awsClient: AmazonCloudWatchClient =
    new AmazonCloudWatchClient {
      setRegion(Region.getRegion(Regions.EU_WEST_1))
    }

  def listMetrics() {
    val metrics = awsClient.listMetrics().getMetrics
    for (metric ← metrics) {
      println(metric)
    }
  }
}
