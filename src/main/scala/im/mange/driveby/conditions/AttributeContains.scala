package im.mange.driveby.conditions

import im.mange.driveby.browser.UnSafeBrowser
import im.mange.driveby.{By, Condition, Describer}

//TODO: get rid of this, or put it cowboy mode only ... AttributeEquals is a much stronger assertion
case class AttributeContains(by: By, attributeName: String, expected: String) extends Condition {
  import Describer._

  def expectation = expect("AttributeContains", List(by.toString, attributeName, expected))
  def isSatisfied(browser: UnSafeBrowser) = { browser.attribute(by, attributeName) contains expected }
  def describeFailure(browser: UnSafeBrowser) = { expectation + butWas(() => browser.attribute(by, attributeName)) }
}