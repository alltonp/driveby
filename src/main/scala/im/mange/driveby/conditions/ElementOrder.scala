package im.mange.driveby.conditions

import im.mange.driveby.browser.UnSafeBrowser
import im.mange.driveby.{By, Condition, Describer}

case class ElementOrder(first: By, second: By) extends Condition {
  import Describer._

  def expectation = expect("ElementOrder", List(first + " location is above " + second))
  def isSatisfied(browser: UnSafeBrowser) = firstElementIsAboveSecondElement(first, second, browser)
  def describeFailure(browser: UnSafeBrowser) = expectation + butWas(() => "firstYLocation: " + yAxisLocation(browser, first) + " secondYLocation: " + yAxisLocation(browser, second) )

  private def firstElementIsAboveSecondElement(first: By, second: By, browser: UnSafeBrowser) = yAxisLocation(browser, first) < yAxisLocation(browser, second)
  private def yAxisLocation(browser: UnSafeBrowser, by: By) = browser.findUniqueElement(by).yAxisLocation
}