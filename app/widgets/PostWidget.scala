package widgets

/**
 *
 * The post widget basically sits idle and waits for
 * HTTP post request to hit the API to update it
 *
 * This kind of widget might be used for things like handling custom application
 * metrics. I.e on 500 error in an application -:> make a HTTP post request to this endpoint to update your dashboard
 *
 */
class PostWidget(key: String) extends Widget {

  def run() = println("Awaiting API requests")
}
