package im.mange.driveby

import browser.{LocalExecutor, ProbingBrowser, InternalBrowser}
import driver.webdriver.{WebDriverFactory, WebDriverDriver}
import tracking.{BrowserOpened, BrowserOpenRequested, Tracker, TrackingIds}

//TODO: move these a separate file/package
trait BrowserType {
  def name: String
}

//TODO: the binary should be Option[String] and default to None
case class Chrome(name: String = "chrome", var driverBinary: String = "No chrome-driver binary was specified", var browserBinary: String = BrowserFactory.defaultBinaryLocationForPlatform) extends BrowserType
case class Firefox(name: String = "firefox", var browserBinary: String = BrowserFactory.defaultBinaryLocationForPlatform) extends BrowserType
//case class HtmlUnit(name: String = "htmlunit") extends BrowserType
case class IE(name: String = "ie", var driverBinary: String = "No ie-driver binary was specified") extends BrowserType
case class Phantomjs(name: String = "phantomjs", var driverBinary: String = "No phantomjs-driver binary was specified") extends BrowserType

object BrowserTypes {
  val chrome = Chrome()
  val firefox = Firefox()
//  val htmlUnit = HtmlUnit()
  val ie = IE()
  val phantomjs = Phantomjs()

  def byName(name: String) = name match {
    case "chrome" => chrome
    case "firefox" => firefox
//    case "htmlUnit" => htmlUnit
    case "ie" => ie
    case "phantomjs" => phantomjs
    case _ => throw new UnsupportedBrowserException("Sorry, don't know how to create a browser of type: " + name)
  }
}

object BrowserFactory {
  val defaultBinaryLocationForPlatform = "default"

  def create(browserType: BrowserType): InternalBrowser = {
    val browserId = TrackingIds.nextBrowserId
    Tracker.add(BrowserOpenRequested(browserType.name, browserId))

    //TODO: this is madness, push creation into BrowserType
    val driver = WebDriverDriver(browserType match {
      case Chrome(_, _, _) => WebDriverFactory.chrome
      case Firefox(_, _) => WebDriverFactory.firefox
      case IE(_, _) => WebDriverFactory.ie
      case Phantomjs(_, _) => WebDriverFactory.phantomjs
      //      case HtmlUnit(_) => WebDriverFactory.htmlunit
      case _ => throw new UnsupportedBrowserException("Sorry, don't know how to create a browser of type: " + browserType)
    })

    if (DriveByConfig.browsersMaximised) driver.maximise()

    val browser = new ProbingBrowser(new LocalExecutor(driver), browserId, 0) with InternalBrowser
    Tracker.add(BrowserOpened(browserType.name, browserId))
    browser
  }
}