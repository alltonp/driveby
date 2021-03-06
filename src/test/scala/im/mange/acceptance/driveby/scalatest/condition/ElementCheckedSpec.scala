package im.mange.acceptance.driveby.scalatest.condition

import im.mange.acceptance.driveby.scalatest.WebSpecification
import im.mange.common.ConditionNotMetException
import im.mange.driveby.Id
import im.mange.driveby.conditions._
import org.scalatest.Matchers

class ElementCheckedSpec extends WebSpecification with Matchers {
  def `pass for id when checked - checkbox` {
    val id = Id("ElementCheckedCheckbox")
    given.page(<body><form><input type="checkbox" checked="checked" id={id.id}/></form></body>)
      .assert(ElementChecked(id))
  }

  def `fail for id not checked - checkbox` {
    val id = Id("ElementCheckedWhenNotCheckedCheckbox")
    val b = given.page(<body><form><input type="checkbox" id={id.id}/></form></body>)

    val thrown = the [ConditionNotMetException] thrownBy { b.assert(ElementChecked(id)) }
    thrown.getMessage should equal("""> FAILED: Assert ElementChecked("Id(ElementCheckedWhenNotCheckedCheckbox)") but was "null"  (not met within 2000 millis)""")
  }

  def `pass for id when checked - radio` {
    val id = Id("ElementCheckedRadio")
    given.page(<body><form><input type="radio" checked="checked" id={id.id}/></form></body>)
      .assert(ElementChecked(id))
  }

  def `fail for id not checked - radio` {
    val id = Id("ElementCheckedWhenNotCheckedRadio")
    val b = given.page(<body><form><input type="radio" id={id.id}/></form></body>)

    val thrown = the [ConditionNotMetException] thrownBy { b.assert(ElementChecked(id)) }
    thrown.getMessage should equal("""> FAILED: Assert ElementChecked("Id(ElementCheckedWhenNotCheckedRadio)") but was "null"  (not met within 2000 millis)""")
  }
}