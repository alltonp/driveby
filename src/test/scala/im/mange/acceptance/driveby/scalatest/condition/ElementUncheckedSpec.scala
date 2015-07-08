package im.mange.acceptance.driveby.scalatest.condition

import im.mange.acceptance.driveby.scalatest.WebSpecification
import im.mange.common.ConditionNotMetException
import im.mange.driveby.Id
import im.mange.driveby.conditions._
import org.scalatest.Matchers

class ElementUncheckedSpec extends WebSpecification with Matchers {
  def `pass for id when unchecked - checkbox` {
    val id = Id("ElementUncheckedCheckbox")
    given.page(<body><form><input type="checkbox" id={id.id}/></form></body>)
      .assert(ElementUnchecked(id))
  }

  def `fail for id not unchecked - checkbox` {
    val id = Id("ElementUncheckedWhenNotUncheckedCheckbox")
    val b = given.page(<body><form><input type="checkbox" checked="checked" id={id.id}/></form></body>)

    val thrown = the [ConditionNotMetException] thrownBy { b.assert(ElementUnchecked(id)) }
    thrown.getMessage should equal("""> FAILED: Assert ElementUnchecked("Id(ElementUncheckedWhenNotUncheckedCheckbox)") but was "true"  (not met within 2000 millis)""")
  }

  def `pass for id when unchecked - radio` {
    val id = Id("ElementUncheckedRadio")
    given.page(<body><form><input type="radio" id={id.id}/></form></body>)
      .assert(ElementUnchecked(id))
  }

  def `fail for id not unchecked - radio` {
    val id = Id("ElementUncheckedWhenNotUncheckedRadio")
    val b = given.page(<body><form><input type="radio" checked="checked" id={id.id}/></form></body>)

    val thrown = the [ConditionNotMetException] thrownBy { b.assert(ElementUnchecked(id)) }
    thrown.getMessage should equal("""> FAILED: Assert ElementUnchecked("Id(ElementUncheckedWhenNotUncheckedRadio)") but was "true"  (not met within 2000 millis)""")
  }
}