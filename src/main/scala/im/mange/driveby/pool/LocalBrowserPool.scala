package im.mange.driveby.pool

import collection.mutable.ListBuffer
import im.mange.driveby.browser.{InternalBrowser, Browser}
import im.mange.common.Times
import im.mange.driveby.{BrowserType, DriveByConfig, Example, BrowserFactory}
import scala.concurrent.ExecutionContext.Implicits.global
import DriveByConfig._
import actors.threadpool.{TimeUnit, LinkedBlockingQueue}
import im.mange.driveby.tracking._

import scala.concurrent.Future

class LocalBrowserPool extends BrowserPool {
  import Times._

  private val allBrowsers = new ListBuffer[InternalBrowser]
  private val availableBrowsers = new LinkedBlockingQueue[Browser]

  def add(browser: BrowserType, instances: Int = 1) {
    times(instances) { add(BrowserFactory.create(browser)) }
  }

  def write(browser: Option[Browser], example: Example) {
    browser match {
      case Some(b) => {
        b.asInstanceOf[InternalBrowser].exampleId = -1
        Tracker.add(BrowserWritten(example.id, b.asInstanceOf[InternalBrowser].id))
        availableBrowsers.put(b)
      }
      case None => Tracker.add(Info("BrowserWriteRequestedForDead", example.id))
    }
  }

  def take(example: Example): Option[Browser] = {
//TODO: do this ...
//    if (terminallyIll) {
//      Tracker.add
//      return None
//    }

    Tracker.add(BrowserTakeRequested(example.id))
    val browser = availableBrowsers.poll(DriveByConfig.browserTakeWaitTimeout, TimeUnit.MILLISECONDS)
    if (browser == null) {
      Tracker.add(BrowserTakeTimeout(example.id))
      None
    } else {
      Tracker.add(BrowserTaken(example.id, browser.asInstanceOf[InternalBrowser].id))
      browser.asInstanceOf[InternalBrowser].exampleId = example.id
      Some(browser)
    }
  }

  def fill() {
    Future {
      //TODO: add BrowserControllers to the println
      println("### Warming up browsers ... " + browserType)
      add(browserType, browserInstances)
    }
  }

  def empty() {
    //TODO: track how long fill and empty take
    allBrowsers.par.map(browser => {
      try {
        Tracker.add(BrowserCloseRequested(browser.id))
        browser.close()
        Tracker.add(BrowserClosed(browser.id))
      } catch {
        case e: Exception => Tracker.add(BrowserCloseFailed(browser.id))
      }
    })
  }

  private def add(browser: InternalBrowser) {
    allBrowsers.append(browser)
    availableBrowsers.put(browser)
  }
}