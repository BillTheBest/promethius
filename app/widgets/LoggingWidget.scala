package widgets

class LoggingWidget extends Widget {

  def run(): Unit = {
    println("Running logging widget")
    val endpoint = "http://api.geonames.org/citiesJSON?north=44.1&south=-9.9&east=-22.4&west=55.2&lang=de&username=demo"
    fetchUrl(endpoint)
  }
}
