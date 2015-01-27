package im.yagni.driveby.conditions

import im.yagni.driveby.browser.UnSafeBrowser
import im.yagni.driveby.{By, Condition, Describer}

case class ElementHidden(by: By) extends Condition {
  import Describer._

  def expectation = expect("ElementHidden", List(by.toString, true.toString))
  def isSatisfied(browser: UnSafeBrowser) = { browser.isDisplayed(by) == false }
  def describeFailure(browser: UnSafeBrowser) = { expectation + butWas(() => browser.isDisplayed(by).toString) }
}