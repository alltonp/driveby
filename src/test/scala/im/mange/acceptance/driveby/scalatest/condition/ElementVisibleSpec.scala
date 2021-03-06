package im.mange.acceptance.driveby.scalatest.condition

import im.mange.acceptance.driveby.scalatest.WebSpecification
import im.mange.common.ConditionNotMetException
import im.mange.driveby.Id
import im.mange.driveby.conditions._
import org.scalatest.Matchers

//TIP: this is a good spec to copy
class ElementVisibleSpec extends WebSpecification with Matchers {
  private val base = "ElementVisible"

  def `work for id` {
    val id = base
    given.page(<b id={id} style="display:block">{id}</b>)
      .assert(theCondition(id))
  }

  def `fail for id not visible` {
    val id = s"$base-WhenHidden"
    val b = given.page(<b id={id} style="display:none">{id}</b>)

    val thrown = the [ConditionNotMetException] thrownBy { b.assert(theCondition(id)) }
    thrown.getMessage should equal(s"""> FAILED: Assert ElementVisible("Id($id)") but was "hidden"  (not met within 2000 millis)""")
  }

  def `fail for id not exist` {
    val id = s"$base-WhenMissing"
    val b = given.page(<b>{id}</b>)

    val thrown = the [ConditionNotMetException] thrownBy { b.assert(theCondition(id)) }
    thrown.getMessage should equal(s"""> FAILED: Assert ElementVisible("Id($id)") but was "does not exist"  (not met within 2000 millis)""")
  }

  private def theCondition(id: String) = ElementVisible(Id(id))
}