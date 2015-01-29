package im.mange.driveby.conditions

import im.mange.driveby.browser.UnSafeBrowser
import im.mange.driveby.{By, Condition}

case class ElementClassesEquals(by: By, classNames: Set[String]) extends Condition {
  import im.mange.driveby.Describer._

  def expectation = expect("ElementClassesEqual", List(by.toString, classNames.mkString(" ")))
  def isSatisfied(browser: UnSafeBrowser) = { browser.attribute(by, "class").split(" ").toSet == classNames }
  def describeFailure(browser: UnSafeBrowser) = { expectation + butWas(() => browser.attribute(by, "class")) }
}