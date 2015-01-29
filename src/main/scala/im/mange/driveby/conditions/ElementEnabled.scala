package im.mange.driveby.conditions

import im.mange.driveby.browser.UnSafeBrowser
import im.mange.driveby.{By, Condition, Describer}

case class ElementEnabled(by: By) extends Condition {
  import Describer._

  def expectation = expect("ElementEnabled", List(by.toString))
  def isSatisfied(browser: UnSafeBrowser) = { browser.isEnabled(by) == true }
  def describeFailure(browser: UnSafeBrowser) = { expectation + butWas(() => browser.isEnabled(by).toString )}
}