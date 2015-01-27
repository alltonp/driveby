package im.yagni.acceptance.driveby.scalatest.browser

import im.yagni.acceptance.driveby.scalatest.WebSpecification
import im.yagni.driveby.conditions._
import org.scalatest.Matchers

class TitleSpec extends WebSpecification with Matchers {
  def `title must be the page title` {
    given.page(<title>page title</title>)
      .assert(TitleEquals("page title"))
  }
}