package im.mange.driveby.conditions

import im.mange.driveby.browser.UnSafeBrowser
import im.mange.driveby.{By, Condition, Describer}

case class ElementHidden(by: By) extends Condition {
  import Describer._

  def expectation = expect("ElementHidden", List(by.toString))
  def isSatisfied(browser: UnSafeBrowser) = { browser.isDisplayed(by) == false }
  def describeFailure(browser: UnSafeBrowser) = { expectation + butWas(() => "visible") }
}