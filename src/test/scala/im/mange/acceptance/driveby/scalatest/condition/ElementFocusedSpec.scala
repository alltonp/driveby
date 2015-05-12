package im.mange.acceptance.driveby.scalatest.condition

import im.mange.acceptance.driveby.scalatest.WebSpecification
import im.mange.common.ConditionNotMetException
import im.mange.driveby.Id
import im.mange.driveby.conditions._
import org.scalatest.Matchers

class ElementFocusedSpec extends WebSpecification with Matchers {
  def `pass for id when focused` {
    val id = Id("ElementFocusedWhenFocused")
    given.page(<body><form><input id={id.id} autofocus="autofocus"/></form></body>)
      .assert(ElementFocused(id))
  }

  def `fail for id not focused` {
    val id = Id("ElementFocusedWhenNotFocused")
    val b = given.page(<body><form><input id={id.id}/></form></body>)

    val thrown = the [ConditionNotMetException] thrownBy { b.assert(ElementFocused(id)) }
    thrown.getMessage should equal("""> FAILED: Assert ElementFocused("Id(ElementFocusedWhenNotFocused)") but was "false"  (not met within 2000 millis)""")
  }
}