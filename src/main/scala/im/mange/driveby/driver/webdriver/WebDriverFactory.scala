package im.mange.driveby.driver.webdriver

import im.mange.driveby.tracking.{BrowserOpenFailed, Tracker}
import org.openqa.selenium.{Dimension, WebDriverException, WebDriver}
import org.openqa.selenium.remote.DesiredCapabilities
import im.mange.driveby._

//TODO: tidy up this complete and utter disgrace

//TIP: webdriver doesnt like too many creations at one ... so sleep a bit if it fails - see http://code.google.com/p/selenium/issues/detail?id=1402
object WebDriverFactory {
  val maxRetryAttempts = 5

  def chrome: WebDriver = {
    import org.openqa.selenium.chrome._

    val capabilities = DesiredCapabilities.chrome()
    if (DriveByConfig.browsersMaximised) {
//      val options = new ChromeOptions()
//      options.addArguments("start-maximized");
//      capabilities.setCapability(ChromeOptions.CAPABILITY, options);
//      capabilities.setCapability("chrome.switches", "--start-maximized")
    }

    if (!BrowserFactory.defaultBinaryLocationForPlatform.equals(BrowserTypes.chrome.browserBinary)) {
      capabilities.setCapability("chrome.binary", BrowserTypes.chrome.browserBinary)
    }

    sys.props += ("webdriver.chrome.driver" -> BrowserTypes.chrome.driverBinary)

    var attempts = 0
    while (attempts < maxRetryAttempts) {
      try {
        return new ChromeDriver(capabilities)
      }
      catch {
        case e: WebDriverException =>
          Tracker.add(BrowserOpenFailed("chrome"))
          Thread.sleep(3000)
          attempts += 1
      }
    }
    throw new BrowserCreationException("failed to start chrome after " + maxRetryAttempts + " attempts")
  }

  def ie: WebDriver = {
    import org.openqa.selenium.ie.InternetExplorerDriver
    import org.apache.http.conn.HttpHostConnectException

    sys.props += ("webdriver.ie.driver" -> BrowserTypes.ie.driverBinary)

    var attempts = 0
    while (attempts < maxRetryAttempts) {
      try {
        return new InternetExplorerDriver
      }
      catch {
        case e: HttpHostConnectException =>
          Tracker.add(BrowserOpenFailed("ie"))
          Thread.sleep(3000)
          attempts += 1
      }
    }
    throw new BrowserCreationException("failed to start ie after " + maxRetryAttempts + " attempts")
  }

  def firefox: WebDriver = {
    if (!BrowserFactory.defaultBinaryLocationForPlatform.equals(BrowserTypes.firefox.browserBinary)) {
      sys.props += ("webdriver.firefox.bin" -> BrowserTypes.firefox.browserBinary)
    }

    var attempts = 0
    while (attempts < maxRetryAttempts) {
      try {
        return new org.openqa.selenium.firefox.FirefoxDriver
      }
      catch {
        case e: WebDriverException =>
          Tracker.add(BrowserOpenFailed("firefox"))
          Thread.sleep(3000)
          attempts += 1
      }
    }
    throw new BrowserCreationException("failed to start firefox after " + maxRetryAttempts + " attempts")
  }

  def phantomjs: WebDriver = {
    import org.openqa.selenium.phantomjs.{PhantomJSDriverService, PhantomJSDriver}
    import org.apache.http.conn.HttpHostConnectException

    val capabilities = new DesiredCapabilities
    capabilities.setJavascriptEnabled(true)
    capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, BrowserTypes.phantomjs.driverBinary)

    var attempts = 0
    while (attempts < maxRetryAttempts) {
      try {
        val driver: PhantomJSDriver = new PhantomJSDriver(capabilities)

        if (DriveByConfig.browsersMaximised) {
          driver.manage().window().setSize(new Dimension(1440, 900))
        }

        return driver
      }
      catch {
        case e: HttpHostConnectException =>
          Tracker.add(BrowserOpenFailed("phantomjs"))
          Thread.sleep(3000)
          attempts += 1
      }
    }
    throw new BrowserCreationException("failed to start phantomjs after " + maxRetryAttempts + " attempts")
//    ???
  }

  //    //TODO: pass through BrowserVersion
//  def htmlunit: WebDriver = {
//    import org.openqa.selenium.htmlunit.HtmlUnitDriver
//    import com.gargoylesoftware.htmlunit._
//
//    val driver = new HtmlUnitDriver(/*BrowserVersion.INTERNET_EXPLORER_7*/) {
//      getWebClient.setAjaxController(new NicelyResynchronizingAjaxController())
//
//      override def get(url: String) {
//        super.get(url)
//        getWebClient.waitForBackgroundJavaScriptStartingBefore(10000)
//        getWebClient.waitForBackgroundJavaScript(10000)
//      }
//    }
//
//    driver.setJavascriptEnabled(true)
//    driver
//
//   //TODO: WebClient.closeAllWindows()
//  }
}