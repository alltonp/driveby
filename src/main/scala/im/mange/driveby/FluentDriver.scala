package im.mange.driveby

import java.io.File

trait FluentDriver extends BrowserAware {
  def assert(condition: Condition, message: String = ""): this.type = { browser.assert(condition, message); this }
  def clear(by: By): this.type = { browser.clear(by); this }
  def click(by: By): this.type = { browser.click(by); this }
  def enter(by: By, value: String, clear: Boolean = false): this.type = { browser.enter(by, value, clear); this }
  def goto(url: String): this.type = { browser.goto(url); this }
  def html: this.type = { browser.html; this }
  def refresh(): this.type = { browser.refresh(); this }
  def screenshot(file: File): this.type = { browser.screenshot(file); this }
  def select(by: By, value: String): this.type = {browser.select(by, value); this}

  def enterAndTabOut(id: String, value: String, clear: Boolean = false, tabOut: Boolean): this.type = {
    enter(Id(id), if (tabOut) s"$value\t" else value, clear); this
  }
}
