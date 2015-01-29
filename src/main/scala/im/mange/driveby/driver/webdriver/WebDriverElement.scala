package im.mange.driveby.driver.webdriver

import im.mange.driveby.driver.NakedElement
import org.openqa.selenium.{By, WebElement}
import collection.JavaConversions._
import org.openqa.selenium.internal.Locatable

case class WebDriverElement(element: WebElement) extends NakedElement {
  def attribute(name: String) = element.getAttribute(name)
  def clear() { element.clear() }
  def click() { element.click() }
  def enter(value: String) { element.sendKeys(value) }
  def isDisplayed = element.isDisplayed
  def isEnabled = element.isEnabled
  def childrenCount = element.findElements(By.xpath(".//*")).size
  def option(value: String) = element.findElements(By.tagName("option")).find(_.getText == value).map(WebDriverElement(_))
//  def scrollTo() { element.asInstanceOf[Locatable].getLocationOnScreenOnceScrolledIntoView }
  def text = element.getText
  def yAxisLocation = element.getLocation.getY
}
