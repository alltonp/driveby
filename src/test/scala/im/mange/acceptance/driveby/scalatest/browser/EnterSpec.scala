package im.mange.acceptance.driveby.scalatest.browser

import im.mange.acceptance.driveby.scalatest.WebSpecification
import im.mange.common.ConditionNotMetException
import im.mange.driveby.Id
import im.mange.driveby.conditions._
import org.scalatest.Matchers

class EnterSpec extends WebSpecification with Matchers {
  def `enter must enter text into a textbox` {
    val id = Id("textBox")
    given.page(<body><form><input type="text" id={id.id}/></form></body>)
      .enter(id, "text")
      .assert(ValueEquals(id, "text"))
  }

  def `enter must append text into a textbox` {
    val id = Id("textBox")
    given.page(<body><form><input type="text" id={id.id}/></form></body>)
      .enter(id, "t")
      .enter(id, "e")
      .enter(id, "x")
      .enter(id, "t")
      .assert(ValueEquals(id, "text"))
  }

  def `enter must first clear textbox text if asked` {
    val id = Id("textBox")
    given.page(<body><form><input type="text" id={id.id}/></form></body>)
      .enter(id, "oldtext", clear = true)
      .enter(id, "newtext", clear = true)
      .assert(ValueEquals(id, "newtext"))
  }

  def `enter must only type if element can be uniquely identified` {
    val id = Id("textBox")
    val b = given.page(<body><form><input type="text" id={id.id}/><br/><input type="text" id={id.id}/></form></body>)
    val thrown = the [ConditionNotMetException] thrownBy { b.enter(id, "blah") }
    thrown.getMessage should equal("""> FAILED: for: Id(textBox) expected 1 element but was "2" (not met within 2000 millis)""")
  }

  def `enter must only type if element is interactable` {
    val id = Id("textBox")
    val b = given.page(<body><form><input type="text" id={id.id} style="display: none"/></form></body>)
    val thrown = the [ConditionNotMetException] thrownBy { b.enter(id, "blah") }
    thrown.getMessage should equal("""> FAILED: for: Id(textBox) expected 1 element but was "0" (not met within 2000 millis)""")
  }
}