package im.yagni.driveby.conditions

import im.yagni.driveby.browser.UnSafeBrowser
import im.yagni.driveby.{By, Condition, Describer}

case class TextEmpty(by: By) extends Condition {
  import Describer._

  def expectation = expect("TextEmpty", List(by.toString, true.toString))
  def isSatisfied(browser: UnSafeBrowser) = { browser.text(by) == "" }
  def describeFailure(browser: UnSafeBrowser) = { expectation + butWas(() => browser.text(by).toString) }
}