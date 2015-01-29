package im.mange.driveby

import report.Reporter
import im.mange.common.OnShutdown
import pool.{BrowserPool, LocalBrowserPool, ApplicationPool}
import tracking.Tracker
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import im.mange.flyby.RemoteBrowserPool

//TODO: please make me non-static
object DriveBy {
  import DriveByConfig._

  lazy val browserPool: BrowserPool = if (DriveByConfig.flyHostname.isEmpty) new LocalBrowserPool() else new RemoteBrowserPool()

  //TODO: think maybe this should be on FlyBy
  def ping(hostname: String) = {
    try { Right(new RemoteBrowserPool(hostname).ping) }
    catch { case e: Throwable => { Left("### Error pinging FlyBy host: " + hostname + ", message: " + e.getMessage) } }
  }

  def start() {
    //TODO: ultimately use browserControllers
    println("### Started DriveBy with applications: " + applicationControllers.size + ", browsers: " + browserInstances)
    Reporter.tidyUp()

    if (cleanupOnShutdown) { OnShutdown.execute("Close browsers and applications", stop _ )}

    OnShutdown.execute("Generate tracking report", Tracker.report _)

    Future(ApplicationPool.fill())
    Future(DriveBy.browserPool.fill())
  }

  def stop() {
    List(DriveBy.browserPool.empty _, ApplicationPool.empty _).par.foreach(_())
  }
}