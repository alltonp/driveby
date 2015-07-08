package im.mange.acceptance.driveby.scalatest.condition

import im.mange.acceptance.driveby.scalatest.WebSpecification
import im.mange.common.ConditionNotMetException
import im.mange.driveby.Id
import im.mange.driveby.conditions._
import org.scalatest.Matchers

class ElementCheckedSpec extends WebSpecification with Matchers {
  def `pass for id when checked - checkbox` {
    val id = Id("ElementChecked-Checkbox")
    given.page(<body><form><input type="checkbox" checked="checked" id={id.id}/></form></body>)
      .assert(ElementChecked(id))
  }

  def `fail for id not checked - checkbox` {
    val id = Id("ElementCheckedWhenNotChecked-Checkbox")
    val b = given.page(<body><form><input type="checkbox" id={id.id}/></form></body>)

    val thrown = the [ConditionNotMetException] thrownBy { b.assert(ElementChecked(id)) }
    thrown.getMessage should equal("""> FAILED: Assert ElementChecked("Id(ElementCheckedCheckboxWhenNotChecked)") but was "null"  (not met within 2000 millis)""")
  }
}