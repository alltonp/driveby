package im.mange.driveby.conditions

import im.mange.driveby.browser.UnSafeBrowser
import im.mange.driveby.{By, Condition, Describer}

case class ElementVisible(by: By) extends Condition {
  import Describer._

  def expectation = expect("ElementVisible", List(by.toString))
  def isSatisfied(browser: UnSafeBrowser) = { browser.isDisplayed(by) == true }
  def describeFailure(browser: UnSafeBrowser) = { expectation + butWas(() => if (browser.exists(by)) "hidden" else "does not exist") }
}