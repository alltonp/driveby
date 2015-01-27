package im.yagni.driveby.conditions

import im.yagni.driveby.browser.UnSafeBrowser
import im.yagni.driveby.{By, Condition, Describer}

case class ElementDisabled(by: By) extends Condition {
  import Describer._

  def expectation = expect("ElementDisabled", List(by.toString))
  def isSatisfied(browser: UnSafeBrowser) = { browser.isEnabled(by) == false }
  def describeFailure(browser: UnSafeBrowser) = { expectation + butWas(() => browser.isEnabled(by).toString ) }
}