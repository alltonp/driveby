package im.mange.acceptance.driveby.scalatest.condition

import im.mange.acceptance.driveby.scalatest.WebSpecification
import im.mange.common.ConditionNotMetException
import im.mange.driveby.Id
import im.mange.driveby.conditions._
import org.scalatest.Matchers

class ElementHiddenSpec extends WebSpecification with Matchers {
  def `work for id` {
    val id = "ElementHidden"
    given.page(<b id={id} style="display:none">{id}</b>)
      .assert(ElementHidden(Id(id)))
  }

  def `fail for id not hidden` {
    val id = "ElementHiddenWhenNotHidden"
    val b = given.page(<b id={id} style="">{id}</b>)

    val thrown = the [ConditionNotMetException] thrownBy { b.assert(ElementHidden(Id(id))) }
    thrown.getMessage should equal("""> FAILED: Assert ElementHidden("Id(ElementHiddenWhenNotHidden)") but was "visible"  (not met within 2000 millis)""")
  }
}