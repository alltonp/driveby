package im.yagni.driveby.pool

import im.yagni.driveby.browser.Browser
import im.yagni.driveby.Example

trait BrowserPool {
  def write(browser: Option[Browser], example: Example)
  def take(example: Example): Option[Browser]
  def fill()
  def empty()
}
