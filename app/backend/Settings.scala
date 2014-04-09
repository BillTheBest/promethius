package backend

import com.typesafe.config.Config

class Settings(config: Config) {

  /**
   * The maximum number of connections
   *
   */
  val maxConnections = config.getInt("ripley.maxConnections")
}
