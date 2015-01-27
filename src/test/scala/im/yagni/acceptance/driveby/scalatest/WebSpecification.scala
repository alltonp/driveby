package im.yagni.acceptance.driveby.scalatest

import im.yagni.WebSpecificationSuite
import im.yagni.common.Host
import im.yagni.acceptance.driveby.driver.BrowserDriver
import im.yagni.driveby.scalatest.{BrowsersAndApplications, Browsers, DriveBySpecification}

//TODO: FooSpec extends Specification with Browsers with Applications with Reporting
//TODO: I'm not sure why it doesnt work with just Browsers actually, because it should do ...
trait WebSpecification extends DriveBySpecification with BrowsersAndApplications {
  WebSpecificationSuite

  private val baseUrl = "http://" + Host.name + ":" + WebSpecificationSuite.PORT
//  private val baseUrl = application.baseUrl

  lazy val given = new BrowserDriver(baseUrl, browser, example)
}
