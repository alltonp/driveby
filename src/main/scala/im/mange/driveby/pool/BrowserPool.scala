package im.mange.driveby.pool

import im.mange.driveby.browser.Browser
import im.mange.driveby.Example

trait BrowserPool {
  def write(browser: Option[Browser], example: Example)
  def take(example: Example): Option[Browser]
  def fill()
  def empty()
}
