package $package$.utils

import java.util.concurrent.Executors

sealed trait CustomExecutor

final object CustomExecutor extends CustomExecutor {

  /**
    * thread counts are hard coded based on optimizations on virtual CPU available on k8
    * sys.runtime.availableProcessors is not a true indications of the available cpu
    * aws kinesis library, kcl, currently is not supporting streaming. Therefor we need to
    * separate the thread-pools to stop KCL putting vital threads to asleep
    */
  final val customExecutor: java.util.concurrent.ExecutorService =
    Executors.newFixedThreadPool(10)
  val ec: java.util.concurrent.ExecutorService =
    Executors.newSingleThreadExecutor()

}
