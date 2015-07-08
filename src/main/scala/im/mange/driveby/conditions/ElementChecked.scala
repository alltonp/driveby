package im.mange.driveby.conditions

import im.mange.driveby.browser.UnSafeBrowser
import im.mange.driveby.{By, Condition, Describer}

case class ElementChecked(by: By) extends Condition {
  import Describer._

  def expectation = expect("ElementChecked", List(by.toString))
  def isSatisfied(browser: UnSafeBrowser) = { !browser.attribute(by, "checked").isEmpty }
  def describeFailure(browser: UnSafeBrowser) = { expectation + butWas(() => browser.attribute(by, "checked") )}
}