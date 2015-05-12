package im.mange.acceptance.driveby.scalatest.condition

import im.mange.acceptance.driveby.scalatest.WebSpecification
import im.mange.common.ConditionNotMetException
import im.mange.driveby.Id
import im.mange.driveby.conditions._
import org.scalatest.Matchers

class ElementEnabledSpec extends WebSpecification with Matchers {
  def `pass for id when enabled` {
    val id = Id("ElementEnabledWhenEnabled")
    given.page(<body><form><input id={id.id}/></form></body>)
      .assert(ElementEnabled(id))
  }

  def `fail for id not enabled` {
    val id = Id("ElementEnabledWhenNotEnabled")
    val b = given.page(<body><form><input disabled="disabled" id={id.id}/></form></body>)

    val thrown = the [ConditionNotMetException] thrownBy { b.assert(ElementEnabled(id)) }
    thrown.getMessage should equal("""> FAILED: Assert ElementEnabled("Id(ElementEnabledWhenNotEnabled)") but was "false"  (not met within 2000 millis)""")
  }
}