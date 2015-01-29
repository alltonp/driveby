package im.mange.driveby.conditions

import im.mange.driveby.browser.UnSafeBrowser
import im.mange.driveby.{By, Condition, Describer}

case class TextEquals(by: By, expected: String) extends Condition {
  import Describer._

  def expectation = expect("TextEquals", List(by.toString, expected))
  def isSatisfied(browser: UnSafeBrowser) = { browser.text(by) == expected }
  def describeFailure(browser: UnSafeBrowser) = { expectation + butWas(() => browser.text(by)) }
}