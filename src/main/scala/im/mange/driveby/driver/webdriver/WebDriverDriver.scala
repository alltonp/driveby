package im.mange.driveby.driver.webdriver

import im.mange.driveby.driver.NakedDriver
import org.openqa.selenium.{OutputType, TakesScreenshot, WebDriver}
import collection.JavaConversions._
import im.mange.driveby.{Class, Id, By}
import im.mange.driveby.browser.UnsupportedByException
import org.openqa.selenium.chrome.ChromeDriver

case class WebDriverDriver(webDriver: WebDriver) extends NakedDriver {
  def goto(url: String) { webDriver.get(url) }
  def title = webDriver.getTitle
  def currentUrl = webDriver.getCurrentUrl
  def html = webDriver.getPageSource
  def close() { webDriver.quit() }
  def findElements(by: By) = webDriver.findElements(convertBy(by)).map(WebDriverElement(_))
  def refresh() { webDriver.get(webDriver.getCurrentUrl) }
  def maximise() { if (!webDriver.isInstanceOf[ChromeDriver]) webDriver.manage().window().maximize() } //TODO: yuk

  def screenshot: Array[Byte] = {
    try {
      if (webDriver.isInstanceOf[TakesScreenshot]) return webDriver.asInstanceOf[TakesScreenshot].getScreenshotAs(OutputType.BYTES)
    } catch {
      case e: Exception => println("Unable to takescreenshot: " + e.getMessage); throw e;
    }
    Array()
  }

  private def convertBy(by: By):org.openqa.selenium.By = {
    by match {
      case Id(id) => org.openqa.selenium.By.id(id)
      case Class(name) => org.openqa.selenium.By.className(name)
      case _ => throw new UnsupportedByException("Unsupported By for this driver")
    }
  }
}