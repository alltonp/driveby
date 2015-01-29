package im.mange.driveby.conditions

import im.mange.driveby.browser.UnSafeBrowser
import im.mange.driveby.{By, Condition, Describer}

case class ValueEquals(by: By, expected: String) extends Condition {
  import Describer._

  def expectation = expect("ValueEquals", List(by.toString, expected))
  def isSatisfied(browser: UnSafeBrowser) = { browser.attribute(by, "value") == expected }
  def describeFailure(browser: UnSafeBrowser) = { expectation + butWas(() => browser.attribute(by, "value")) }
}