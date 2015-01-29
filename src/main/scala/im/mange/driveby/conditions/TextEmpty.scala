package im.mange.driveby.conditions

import im.mange.driveby.browser.UnSafeBrowser
import im.mange.driveby.{By, Condition, Describer}

case class TextEmpty(by: By) extends Condition {
  import Describer._

  def expectation = expect("TextEmpty", List(by.toString, true.toString))
  def isSatisfied(browser: UnSafeBrowser) = { browser.text(by) == "" }
  def describeFailure(browser: UnSafeBrowser) = { expectation + butWas(() => browser.text(by).toString) }
}