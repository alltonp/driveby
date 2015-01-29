package im.mange.driveby.conditions

import im.mange.driveby.{Describer, By, Condition}
import im.mange.driveby.browser.UnSafeBrowser
import im.mange.driveby.driver.webdriver.WebDriverElement

case class SelectOptionsEquals(by: By, expected: Seq[String]) extends Condition {
  import Describer._
  import scala.collection.JavaConversions.iterableAsScalaIterable

  private def options(browser: UnSafeBrowser): Seq[String] = browser.findUniqueElement(by).asInstanceOf[WebDriverElement]
    .element.findElements(org.openqa.selenium.By.tagName("option")).map (_.getText).toSeq

  def expectation = expect("SelectOptionsEquals", List(by.toString, expected.toString()))
  def isSatisfied(browser: UnSafeBrowser) = options(browser) == expected
  def describeFailure(browser: UnSafeBrowser) = { expectation + butWas(() => options(browser).toString()) }
}