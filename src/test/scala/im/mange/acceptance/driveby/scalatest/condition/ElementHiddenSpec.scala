package im.mange.acceptance.driveby.scalatest.condition

import im.mange.acceptance.driveby.scalatest.WebSpecification
import im.mange.common.ConditionNotMetException
import im.mange.driveby.Id
import im.mange.driveby.conditions._
import org.scalatest.Matchers

class ElementHiddenSpec extends WebSpecification with Matchers {
  private val base = "ElementHidden"

  def `work for id` {
    val id = base
    given.page(<b id={id} style="display:none">{id}</b>)
      .assert(ElementHidden(Id(id)))
  }

  def `fail for id not hidden` {
    val id = s"$base-WhenNotHidden"
    val b = given.page(<b id={id} style="">{id}</b>)

    val thrown = the [ConditionNotMetException] thrownBy { b.assert(ElementHidden(Id(id))) }
    thrown.getMessage should equal(s"""> FAILED: Assert ElementHidden("Id($id)") but was "visible"  (not met within 2000 millis)""")
  }

  def `fail for id not exist` {
    val id = s"$base-WhenMissing"
    val b = given.page(<b>{id}</b>)

    val thrown = the [ConditionNotMetException] thrownBy { b.assert(ElementHidden(Id(id))) }
    thrown.getMessage should equal(s"""> FAILED: Assert ElementHidden("Id($id)") but was "does not exist"  (not met within 2000 millis)""")
  }

}