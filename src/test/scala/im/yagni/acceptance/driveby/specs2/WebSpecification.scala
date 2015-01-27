package im.yagni.acceptance.driveby.specs2

import im.yagni.driveby.specs2.{DriveBySpecification, Browsers}
import im.yagni.WebSpecificationSuite
import im.yagni.common.Host
import im.yagni.acceptance.driveby.driver.BrowserDriver

//TODO: whack this and use one of the others ...
trait WebSpecification extends DriveBySpecification with Browsers {
  WebSpecificationSuite

  private val baseUrl = "http://" + Host.name + ":" + WebSpecificationSuite.PORT

  trait WebExample[T] extends DriveByExample[T] with Browsers

  def rawBrowser = new WebExample[BrowserDriver] {
    def outside: BrowserDriver = new BrowserDriver(baseUrl, browser, example)
  }
}
