package im.mange.acceptance.driveby.scalatest.condition

import im.mange.acceptance.driveby.scalatest.WebSpecification
import im.mange.common.ConditionNotMetException
import im.mange.driveby.Id
import im.mange.driveby.conditions._
import org.scalatest.Matchers

class ElementDisabledSpec extends WebSpecification with Matchers {
  def `pass for id when disabled` {
    val id = Id("ElementDisabledWhenDisabled")
    given.page(<body><form><input disabled="disabled" id={id.id}/></form></body>)
      .assert(ElementDisabled(id))
  }

  def `fail for id not enabled` {
    val id = Id("ElementNotDisabledWhenNotDisabled")
    val b = given.page(<body><form><input id={id.id}/></form></body>)

    val thrown = the [ConditionNotMetException] thrownBy { b.assert(ElementDisabled(id)) }
    thrown.getMessage should equal("""> FAILED: Assert ElementDisabled("Id(ElementNotDisabledWhenNotDisabled)") but was "true"  (not met within 2000 millis)""")
  }
}