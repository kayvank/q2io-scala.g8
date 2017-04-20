package $package$.utils

import com.typesafe.config.ConfigFactory

object Global {

  val cfg = ConfigFactory.load
  val cfgV = cfg.getConfig("v")
}
