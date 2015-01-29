package im.mange.driveby.conditions

import im.mange.driveby.browser.UnSafeBrowser
import im.mange.driveby.{By, Condition, Describer}

//TODO: get rid of this, or put it cowboy mode only ... TextEquals is a much stronger assertion
case class TextContains(by: By, expected: String) extends Condition {
  import Describer._

  def expectation = expect("TextContains", List(by.toString, expected))
  def isSatisfied(browser: UnSafeBrowser) = { browser.text(by).contains(expected) }
  def describeFailure(browser: UnSafeBrowser) = { expectation + butWas(() => browser.text(by)) }
}