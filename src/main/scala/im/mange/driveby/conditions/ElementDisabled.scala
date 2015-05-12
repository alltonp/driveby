package im.mange.driveby.conditions

import im.mange.driveby.browser.UnSafeBrowser
import im.mange.driveby.{By, Condition, Describer}

case class ElementDisabled(by: By) extends Condition {
  import Describer._

  def expectation = expect("ElementDisabled", List(by.toString))
  def isSatisfied(browser: UnSafeBrowser) = { browser.isEnabled(by) == false }
  def describeFailure(browser: UnSafeBrowser) = { expectation + butWas(() => browser.isEnabled(by).toString ) }
}

case class ElementFocused(by: By) extends Condition {
  import Describer._

  def expectation = expect("ElementFocused", List(by.toString))
  def isSatisfied(browser: UnSafeBrowser) = { browser.hasFocus(by) == true }
  def describeFailure(browser: UnSafeBrowser) = { expectation + butWas(() => browser.hasFocus(by).toString ) }
}