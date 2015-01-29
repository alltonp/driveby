package im.mange.flyby

import im.mange.driveby.pool.BrowserPool
import im.mange.driveby.browser.{ProbingBrowser, InternalBrowser, Browser}
import im.mange.driveby.{DriveByConfig, Example}
import scala.None
import im.mange.driveby.tracking._
import org.joda.time.DateTime

//TODO: we should respect the timeouts for taking a browser (in both local and remote)
class RemoteBrowserPool(hostname: String = DriveByConfig.flyHostname.get) extends BrowserPool {
  private val space = new FlySpace(hostname)

  def ping = space.readMany(RemoteBrowser()).map(_.asInstanceOf[RemoteBrowser]).toList

  //TODO: needs to mirror the version in LocalBrowserPool
  def write(browser: Option[Browser], example: Example) {
    val b = browser.get.asInstanceOf[InternalBrowser]
    b.exampleId = -1
    Tracker.add(BrowserWritten(example.id, b.id))
    //TODO: need to pass through browserType and hostname - as currently being lost
    space.take(RemoteBrowser(browserId = b.id, free = false))
    space.write(RemoteBrowser(browserId = b.id, free = true, mileage = b.mileage, lastUsed = DateTime.now()/*, browserType = b.?? */))
  }

  //TODO: needs to mirror the version in LocalBrowserPool
  def take(example: Example): Option[Browser] = {
    Tracker.add(BrowserTakeRequested(example.id))

    //TODO: we should respect the browserType here ..
    val b = space.take(RemoteBrowser(free = true), DriveByConfig.browserTakeWaitTimeout)

    b match {
      case Some(b2) => {
        val browser = new ProbingBrowser(new RemoteExecutor(space, b2.browserId), b2.browserId, b2.mileage) with InternalBrowser
        space.write(b2.copy(free = false, lastUsed = DateTime.now()))

        Tracker.add(BrowserTaken(example.id, browser.asInstanceOf[InternalBrowser].id))
        Some(browser.asInstanceOf[Browser])
      }
      case None => {
        Tracker.add(BrowserTakeTimeout(example.id))
        None
      }
    }
  }

  def fill() {}
  def empty() {}
}