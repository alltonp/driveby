package im.mange.acceptance.driveby.scalatest.condition

import im.mange.acceptance.driveby.scalatest.WebSpecification
import im.mange.common.ConditionNotMetException
import im.mange.driveby.Id
import im.mange.driveby.conditions._
import org.scalatest.Matchers

class ElementClassesContainsSpec extends WebSpecification with Matchers {
  def `pass for id` {
    val id = Id("hasClassWith")
    given.page(<b id={id.id} class="wobbleClass hasClass wibbleClass">hasClassWith</b>)
      .assert(ElementClassesContains(id, "hasClass"))
  }

  def `fail for id without` {
    val id = Id("hasClassWithout")
    val b = given.page(<b id={id.id} class="nothasClass">hasClassWithout</b>)

    val thrown = the [ConditionNotMetException] thrownBy { b.assert(ElementClassesContains(id, "hasClass")) }
    thrown.getMessage should equal( """> FAILED: Assert ElementClassesContains("Id(hasClassWithout)", "hasClass") but was "nothasClass"  (not met within 2000 millis)""")
  }

}