package im.mange.driveby.browser

import im.mange.driveby.driver.{NakedElement, NakedDriver}
import im.mange.driveby.commands._
import im.mange.common.Wait._
import im.mange.driveby.{BrowserCommand, Condition, By, CommandExecutor}

class LocalExecutor(driver: NakedDriver) extends CommandExecutor {
  private val unsafe = new UnSafeBrowser(driver)

  def execute(command: BrowserCommand) = {
    command match {
      case a@Assert(condition, message, result) => a.result = assert(condition, message); command
      case Clear(by) => unsafe.findUniqueInteractableElement(by).clear(); command
      case Click(by) => unsafe.findUniqueInteractableElement(by).click(); command
      case Close() => driver.close(); command

      case Enter(by, value, clear) => {
        val e = unsafe.findUniqueInteractableElement(by)
        if (clear) e.clear()
        e.enter(value)
      }; command

      case Goto(url) => driver.goto(url); command
      case h@Html(result) => h.result = driver.html; command
      case Refresh() => driver.refresh(); command
      case Select(by, value) => select(by, value); command
//      case ScrollTo(by) => unsafe.findUniqueInteractableElement(by).scrollTo(); command
      case s@Screenshot(result) => s.result = driver.screenshot; command
      case _ => throw new UnsupportedBrowserCommandException("Do not know how to process: " + command)
    }
  }

  private def select(by: By, value: String) {
    waitUpTo().forCondition(
      unsafe.findOption(by, value) match {
        case Some(option) => {
          option.click()
          true
        }
        case _ => false
      }, "select '" + by + "' option '" + value + "'"
    )
  }

  private def assert(condition: Condition, additionalMessage: String = "") = {
    waitUpTo().forCondition(
      condition.isSatisfied(unsafe),
      try {
        "Assert " + condition.describeFailure(unsafe) + " " + additionalMessage
      }
      catch {
        case e: Exception => e.getMessage
      }
    )
    true
  }
}
