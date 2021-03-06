package im.mange.driveby.conditions

import im.mange.driveby.browser.UnSafeBrowser
import im.mange.driveby.{By, Condition, Describer}

case class AttributeEquals(by: By, attributeName: String, expected: String) extends Condition {
  import Describer._

  def expectation = expect("AttributeEquals", List(by.toString, attributeName, expected))
  def isSatisfied(browser: UnSafeBrowser) = { browser.attribute(by, attributeName) == expected }
  def describeFailure(browser: UnSafeBrowser) = { expectation + butWas(() => browser.attribute(by, attributeName)) }
}