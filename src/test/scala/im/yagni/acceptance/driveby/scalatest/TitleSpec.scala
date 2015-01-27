package im.yagni.acceptance.driveby.scalatest

import im.yagni.driveby.conditions._
import im.yagni.driveby.Id
import im.yagni.common.ConditionNotMetException
import org.scalatest.Matchers

class TitleSpec extends WebSpecification with Matchers {
  def `title must be the page title` {
    given.page(<title>page title</title>)
      .assert(TitleEquals("page title"))
  }
}