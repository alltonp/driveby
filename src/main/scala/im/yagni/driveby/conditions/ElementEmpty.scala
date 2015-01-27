package im.yagni.driveby.conditions

import im.yagni.driveby.browser.UnSafeBrowser
import im.yagni.driveby.{By, Condition, Describer}

case class ElementEmpty(by: By) extends Condition {
  import Describer._

  def expectation = expect("ElementEmpty", List(by.toString, true.toString))

  def isSatisfied(browser: UnSafeBrowser) = {
    browser.findUniqueElement(by).childrenCount == 0 &&
    browser.findUniqueElement(by).text.isEmpty
  }

  def describeFailure(browser: UnSafeBrowser) = { expectation + butWas(
    () => "Element has " + browser.findUniqueElement(by).childrenCount.toString + " children " +
          "and \"" + browser.findUniqueElement(by).text + "\" text" ) }
}