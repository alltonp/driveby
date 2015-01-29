package im.mange.driveby.conditions

import im.mange.driveby.{Condition, Describer}
import im.mange.driveby.browser.UnSafeBrowser

//TODO: get rid of this, or put it cowboy mode only ... CurrentUrlEquals is a much stronger assertion
case class CurrentUrlContains(expected: String) extends Condition {
  import Describer._

  def expectation = expect("CurrentUrlContains", List(expected))
  def isSatisfied(browser: UnSafeBrowser) = { browser.currentUrl.contains(expected) }
  def describeFailure(browser: UnSafeBrowser) = { expectation + butWas(() => browser.currentUrl) }
}