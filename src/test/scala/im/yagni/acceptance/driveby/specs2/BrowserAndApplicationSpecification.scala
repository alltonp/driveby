package im.yagni.acceptance.driveby.specs2

import im.yagni.driveby.specs2.{BrowsersAndApplications, DriveBySpecification}
import im.yagni.driveby.browser.Browser
import im.yagni.driveby.pool.Application

case class BrowserAndApplicationDriver(browser: Browser, application: Application)

trait BrowserAndApplicationSpecification extends DriveBySpecification with BrowsersAndApplications {
  trait WebExample[T] extends DriveByExample[T] with BrowsersAndApplications {

    def driver = new WebExample[BrowserAndApplicationDriver] {
      def outside: BrowserAndApplicationDriver = new BrowserAndApplicationDriver(browser, application)
    }
  }
}
