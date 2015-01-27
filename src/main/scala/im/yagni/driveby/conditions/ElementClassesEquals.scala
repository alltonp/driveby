package im.yagni.driveby.conditions

import im.yagni.driveby.browser.UnSafeBrowser
import im.yagni.driveby.{By, Condition}

case class ElementClassesEquals(by: By, classNames: Set[String]) extends Condition {
  import im.yagni.driveby.Describer._

  def expectation = expect("ElementClassesEqual", List(by.toString, classNames.mkString(" ")))
  def isSatisfied(browser: UnSafeBrowser) = { browser.attribute(by, "class").split(" ").toSet == classNames }
  def describeFailure(browser: UnSafeBrowser) = { expectation + butWas(() => browser.attribute(by, "class")) }
}