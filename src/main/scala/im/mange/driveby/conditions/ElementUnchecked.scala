package im.mange.driveby.conditions

import im.mange.driveby.browser.UnSafeBrowser
import im.mange.driveby.{By, Condition, Describer}

case class ElementUnchecked(by: By) extends Condition {
  import Describer._

  def expectation = expect("ElementUnchecked", List(by.toString))
  def isSatisfied(browser: UnSafeBrowser) = { browser.attribute(by, "checked") == null }
  def describeFailure(browser: UnSafeBrowser) = { expectation + butWas(() => browser.attribute(by, "checked") )}
}