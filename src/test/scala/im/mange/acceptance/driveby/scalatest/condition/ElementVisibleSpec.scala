package im.mange.acceptance.driveby.scalatest.condition

import im.mange.acceptance.driveby.scalatest.WebSpecification
import im.mange.common.ConditionNotMetException
import im.mange.driveby.Id
import im.mange.driveby.conditions._
import org.scalatest.Matchers

class ElementVisibleSpec extends WebSpecification with Matchers {
  def `work for id` {
    val id = "ElementVisible"
    given.page(<b id={id} style="display:block">{id}</b>)
      .assert(ElementVisible(Id(id)))
  }

  def `fail for id not visible` {
    val id = "ElementVisibleWhenHidden"
    val b = given.page(<b id={id} style="display:none">{id}</b>)

    val thrown = the [ConditionNotMetException] thrownBy { b.assert(ElementVisible(Id(id))) }
    thrown.getMessage should equal("""> FAILED: Assert ElementVisible("Id(ElementVisibleWhenHidden)") but was "false"  (not met within 2000 millis)""")
  }

}