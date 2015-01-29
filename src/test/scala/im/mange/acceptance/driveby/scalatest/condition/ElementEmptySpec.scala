package im.mange.acceptance.driveby.scalatest.condition

import im.mange.acceptance.driveby.scalatest.WebSpecification
import im.mange.common.ConditionNotMetException
import im.mange.driveby.Id
import im.mange.driveby.conditions._
import org.scalatest.Matchers

class ElementEmptySpec extends WebSpecification with Matchers {
  def `pass for id when no children and no text` {
    val id = Id("ElementEmpty")
    given.page(<b id={id.id}></b>)
      .assert(ElementEmpty(id))
  }

  def `fail for id with children` {
    val id = Id("ElementEmptyWithChild")
    val b = given.page(<b id={id.id}><div></div></b>)

    val thrown = the [ConditionNotMetException] thrownBy { b.assert(ElementEmpty(id)) }
    thrown.getMessage should equal("""> FAILED: Assert ElementEmpty("Id(ElementEmptyWithChild)", "true") but was "Element has 1 children and "" text"  (not met within 2000 millis)""")
  }

  def `fail for id with text` {
    val id = Id("ElementEmptyWithText")
    val b = given.page(<b id={id.id}>textInElement</b>)

    val thrown = the [ConditionNotMetException] thrownBy { b.assert(ElementEmpty(id)) }
    thrown.getMessage should equal("""> FAILED: Assert ElementEmpty("Id(ElementEmptyWithText)", "true") but was "Element has 0 children and "textInElement" text"  (not met within 2000 millis)""")
  }

}