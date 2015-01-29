package im.mange.driveby.conditions

import im.mange.driveby.browser.UnSafeBrowser
import im.mange.driveby.{By, Condition, Describer}

case class ElementCountEquals(by: By, expected: Int) extends Condition {
  import Describer._

  def expectation = expect("ElementCountEquals", List(by.toString, expected.toString))
  def isSatisfied(browser: UnSafeBrowser) = { browser.findElements(by).size == expected }
  def describeFailure(browser: UnSafeBrowser) = { expectation + butWas(() => browser.findElements(by).size.toString) }
}