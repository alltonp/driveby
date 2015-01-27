package im.yagni.driveby.conditions

import im.yagni.driveby.browser.UnSafeBrowser
import im.yagni.driveby.{By, Condition, Describer}

case class ValueEquals(by: By, expected: String) extends Condition {
  import Describer._

  def expectation = expect("ValueEquals", List(by.toString, expected))
  def isSatisfied(browser: UnSafeBrowser) = { browser.attribute(by, "value") == expected }
  def describeFailure(browser: UnSafeBrowser) = { expectation + butWas(() => browser.attribute(by, "value")) }
}