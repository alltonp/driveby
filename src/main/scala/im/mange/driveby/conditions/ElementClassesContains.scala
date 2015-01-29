package im.mange.driveby.conditions

import im.mange.driveby.{Describer, Condition, By}
import im.mange.driveby.browser.UnSafeBrowser

case class ElementClassesContains(by: By, className: String) extends Condition {
  import Describer._

  def expectation = expect("ElementClassesContains", List(by.toString, className))
  def isSatisfied(browser: UnSafeBrowser) = { browser.attribute(by, "class").split(" ") contains className }
  def describeFailure(browser: UnSafeBrowser) = { expectation + butWas(() => browser.attribute(by, "class")) }
}