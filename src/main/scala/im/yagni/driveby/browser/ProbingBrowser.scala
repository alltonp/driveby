package im.yagni.driveby.browser

import im.yagni.driveby.commands._
import org.apache.commons.io.FileUtils
import java.io.File
import im.yagni.common.ConditionNotMetException
import im.yagni.driveby.tracking.{BrowserCommandExecuted, Tracker}
import im.yagni.driveby.{CommandExecutor, BrowserCommand, By, Condition}

case class ProbingBrowser(private val executor: CommandExecutor, id: Long, var mileage: Long) extends Browser {
  def execute(command: BrowserCommand) = {
    try {
      Tracker.add(BrowserCommandExecuted(command, this.asInstanceOf[InternalBrowser].exampleId))
      mileage += 1
      executor.execute(command)
    } catch {
      case e: UnsupportedBrowserCommandException => throw e
      case e: ConditionNotMetException => throw e
      case e: Throwable => {
        println("### Something really bad occured while executing a command, the browser may be dead")
        println("### ... the command was: " + command)
        println("### ... the browser was: " + this)
        e.printStackTrace()
        throw e
      }
    }
  }
  
  def assert(condition: Condition, additionalMessage: String = "") = execute(Assert(condition, additionalMessage)).asInstanceOf[Assert].result
  def clear(by: By) = { execute(Clear(by)); this }
  def click(by: By) = { execute(Click(by)); this }
  def close() { execute(Close()) }
  def enter(by: By, value: String, clear: Boolean = false) = { execute(Enter(by, value, clear)); this }
  def goto(url: String) = { execute(Goto(url)); this }
  def html = execute(Html()).asInstanceOf[Html].result
  def refresh() = { execute(Refresh()); this }
  def screenshot(file: File) { FileUtils.writeByteArrayToFile(file, execute(Screenshot()).asInstanceOf[Screenshot].result) }
  def select(by: By, value: String) = { execute(Select(by, value)); this }
//  def scrollTo(by: By) = { execute(ScrollTo(by)); this }
}
