package im.mange.acceptance.driveby.scalatest.condition

import im.mange.acceptance.driveby.scalatest.WebSpecification
import im.mange.common.ConditionNotMetException
import im.mange.driveby.Id
import im.mange.driveby.conditions._
import org.scalatest.Matchers

class ElementClassesEqualsSpec extends WebSpecification with Matchers {
  def `pass for id` {
    val id = Id("hasClassWith")
    given.page(<b id={id.id} class="wobbleClass hasClass wibbleClass">hasClassWith</b>)
      .assert(ElementClassesEquals(id, Set("hasClass", "wibbleClass", "wobbleClass")))
  }

  def `fail for id with missing` {
    val id = Id("hasClassWithMissing")
    val b = given.page(<b id={id.id} class="nothasClass">hasClassWithMissing</b>)

    val thrown = the [ConditionNotMetException] thrownBy { b.assert(ElementClassesEquals(id, Set("hasClass"))) }
    thrown.getMessage should equal("""> FAILED: Assert ElementClassesEqual("Id(hasClassWithMissing)", "hasClass") but was "nothasClass"  (not met within 2000 millis)""")
  }

  def `fail for id with extra` {
    val id = Id("hasClassWithExtra")
    val b = given.page(<b id={id.id} class="hasClass extraClass">hasClassWithExtra</b>)

    val thrown = the [ConditionNotMetException] thrownBy { b.assert(ElementClassesEquals(id, Set("hasClass"))) }
    thrown.getMessage should equal("""> FAILED: Assert ElementClassesEqual("Id(hasClassWithExtra)", "hasClass") but was "hasClass extraClass"  (not met within 2000 millis)""")
  }
}