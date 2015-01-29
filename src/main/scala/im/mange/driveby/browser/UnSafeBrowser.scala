package im.mange.driveby.browser

import im.mange.common.Wait._
import im.mange.driveby.{By, Condition, Describer}
import im.mange.driveby.driver.NakedDriver
import collection.JavaConversions._

class UnSafeBrowser private[driveby] (driver: NakedDriver) {
  def currentUrl = driver.currentUrl
  def title = driver.title
  def text(by: By) = findUniqueElement(by).text
  def attribute(by: By, attribute: String) = findUniqueElement(by).attribute(attribute)
  def isDisplayed(by: By) = findUniqueElement(by).isDisplayed
  def isEnabled(by: By) = findUniqueElement(by).isEnabled

  def exists(by: By) = {
    try {
      findUniqueElement(by)
      true
    } catch {
      case e: Exception => false
    }
  }

  def findUniqueElement(by: By) = {
    val condition = UniqueElement(by)
    waitUpTo().forCondition(condition.isSatisfied(this), condition.describeFailure(this))
    driver.findElements(by).get(0)
  }

  def findUniqueInteractableElement(by: By) = {
    val condition = UniqueInteractableElement(by)
    waitUpTo().forCondition(condition.isSatisfied(this), condition.describeFailure(this))
    driver.findElements(by).get(0)
  }

  def findElements(by: By) =
    try {
      driver.findElements(by).toList
    } catch {
      case e: Exception => Nil
    }

  def findOption(selectBy: By, value: String) = findUniqueInteractableElement(selectBy).option(value)

  case class UniqueElement(by: By) extends Condition {
    import Describer._

    def expectation = expect("UniqueElement", List(by.toString))
    def isSatisfied(browser: UnSafeBrowser) = { browser.findElements(by).size == 1 }
    def describeFailure(browser: UnSafeBrowser) = { "for: " + by + " expected 1 element" + butWas(() => browser.findElements(by).size.toString) }
  }

  case class UniqueInteractableElement(by: By) extends Condition {
    import Describer._

    def expectation = expect("UniqueInteractableElement", List(by.toString))

    def isSatisfied(browser: UnSafeBrowser) = {
      val elements = driver.findElements(by)
      elements.size == 1 && elements.head.isDisplayed
    }

    def describeFailure(browser: UnSafeBrowser) = { "for: " + by + " expected 1 element" + butWas(() => browser.findElements(by).filter(_.isDisplayed).size.toString) }
  }
}