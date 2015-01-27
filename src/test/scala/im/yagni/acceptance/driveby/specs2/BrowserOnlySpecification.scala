package im.yagni.acceptance.driveby.specs2

import im.yagni.driveby.specs2.{DriveBySpecification, Browsers}
import im.yagni.driveby.browser.Browser

case class BrowserOnlyDriver(browser: Browser)

trait BrowserOnlySpecification extends DriveBySpecification with Browsers {
  trait WebExample[T] extends DriveByExample[T] with Browsers

  def driver = new WebExample[BrowserOnlyDriver] {
    def outside: BrowserOnlyDriver = new BrowserOnlyDriver(browser)
  }
}