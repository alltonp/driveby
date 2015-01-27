package im.yagni.driveby.conditions

import im.yagni.driveby.{Condition, Describer}
import im.yagni.driveby.browser.UnSafeBrowser

case class CurrentUrlEquals(expected: String) extends Condition {
  import Describer._

  def expectation = expect("CurrentUrlEquals", List(expected))
  def isSatisfied(browser: UnSafeBrowser) = { browser.currentUrl == expected }
  def describeFailure(browser: UnSafeBrowser) = { expectation + butWas(() => browser.currentUrl) }
}