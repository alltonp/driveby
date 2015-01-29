package im.mange.acceptance.driveby.scalatest.condition

import im.mange.acceptance.driveby.scalatest.WebSpecification
import im.mange.common.ConditionNotMetException
import im.mange.driveby.Id
import im.mange.driveby.conditions._
import org.scalatest.Matchers

class TextEmptySpec extends WebSpecification with Matchers {
  def `pass for id` {
    val id = Id("TextEmpty")
    given.page(<b id={id.id}></b>)
      .assert(TextEmpty(id))
  }

  def `pass for id with children` {
    val id = Id("TextEmptyWithChild")
    given.page(<b id={id.id}><div></div></b>)
      .assert(TextEmpty(id))
  }

  def `fail for id with text` {
    val id = Id("TextEmptyWithText")
    val b = given.page(<b id={id.id}>text</b>)

    val thrown = the [ConditionNotMetException] thrownBy { b.assert(TextEmpty(id)) }
    thrown.getMessage should equal("""> FAILED: Assert TextEmpty("Id(TextEmptyWithText)", "true") but was "text"  (not met within 2000 millis)""")
  }

  def `fail for id with children with text` {
    val id = Id("TextEmptyWithChildWithText")
    val b = given.page(<b id={id.id}><div>childtext</div></b>)

    val thrown = the [ConditionNotMetException] thrownBy { b.assert(TextEmpty(id)) }
    thrown.getMessage should equal("""> FAILED: Assert TextEmpty("Id(TextEmptyWithChildWithText)", "true") but was "childtext"  (not met within 2000 millis)""")
  }
}