package im.yagni.driveby.browser

import im.yagni.driveby.By
import im.yagni.driveby.Condition
import java.io.File

trait Browser {
  def assert(condition: Condition, message: String = ""): Boolean
  def clear(by: By): Browser
  def click(by: By): Browser
  def enter(by: By, value: String, clear: Boolean = false): Browser
  def goto(url: String): Browser
  def html: String
  def refresh(): Browser
  def screenshot(file: File)//TODO: should return bytes
//  def scrollTo(by: By): Browser //TODO: remove me
  def select(by: By, value: String): Browser
}