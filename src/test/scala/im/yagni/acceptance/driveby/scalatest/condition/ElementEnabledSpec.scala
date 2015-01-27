package im.yagni.acceptance.driveby.scalatest.condition

import im.yagni.acceptance.driveby.scalatest.WebSpecification
import im.yagni.common.ConditionNotMetException
import im.yagni.driveby.Id
import im.yagni.driveby.conditions._
import org.scalatest.Matchers

class ElementEnabledSpec extends WebSpecification with Matchers {
  def `pass for id when enabled` {
    val id = Id("ElementEnabledWhenEnabled")
    given.page(<form><input id={id.id}/></form>)
      .assert(ElementEnabled(id))
  }

  def `fail for id not enabled` {
    val id = Id("ElementEnabledWhenNotEnabled")
    val b = given.page(<form><input disabled="disabled" id={id.id}/></form>)

    val thrown = the [ConditionNotMetException] thrownBy { b.assert(ElementEnabled(id)) }
    thrown.getMessage should equal("""> FAILED: Assert ElementEnabled("Id(ElementEnabledWhenNotEnabled)") but was "false"  (not met within 2000 millis)""")
  }
}