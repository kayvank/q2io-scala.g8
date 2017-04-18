package utils

import kamon.Kamon

object Monitor {

  val kclCntr =
    Kamon.metrics.counter("kcl-published")

  val dbCntr =
    Kamon.metrics.counter("db-insert")

  val telemetryCntr =
    Kamon.metrics.counter("qos_telemetry")

  val kclToStringExceptionCntr =
    Kamon.metrics.counter("kcl-parsing-exceptions")

  val noQosCntr =
    Kamon.metrics.counter("endo-with no-qos")

  val jsonDecoingExceptionCntr =
    Kamon.metrics.counter("endo-json-decoding-failure")

  val kclStringToJsonExcpetionCntr =
    Kamon.metrics.counter("string-to-json-parsing-failure")

  val kclRetriableExceptionCntr =
    Kamon.metrics.counter("kcl-reryable-Exception")

  val unKnownExceptionCntr =
    Kamon.metrics.counter("unknown-Exception")

}
