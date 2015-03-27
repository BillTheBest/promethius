package backend

import play.api.Play
import play.api.Play.current

object Settings {

  val pollFrequency =
    Play.configuration.getInt("promethius.pollingFrequency").getOrElse(5)
}
