package im.yagni.acceptance.driveby.scalatest

import im.yagni.common.ConditionNotMetException
import im.yagni.driveby.Id
import im.yagni.driveby.conditions._
import org.scalatest.Matchers

class SelectSpec extends WebSpecification with Matchers {
  def `select must select an option` {
    val id = Id("select")
    given.page(<body><form><select id={id.id}><option>1</option><option>2</option></select></form></body>)
      .select(id, "2")
      .assert(ValueEquals(id, "2"))
  }

  def `only select if element can be uniquely identified` {
    val id = Id("select")
    val b = given.page(<body><form><select id={id.id}/><br/><select id={id.id}/></form></body>)

    val thrown = the [ConditionNotMetException] thrownBy { b.select(id, "blah") }
    thrown.getMessage should equal("""> FAILED: select 'Id(select)' option 'blah' (not met within 2000 millis)""")
  }

  def `only select if element is interactable` {
    val id = Id("select")
    val b = given.page(<body><form><select id={id.id} style="display: none"/></form></body>)

    val thrown = the [ConditionNotMetException] thrownBy { b.select(id, "blah") }
    thrown.getMessage should equal("""> FAILED: select 'Id(select)' option 'blah' (not met within 2000 millis)""")
   }

  def `only select if option is present` {
    val id = Id("select")
    val b = given.page(<body><form><select id={id.id}><option>1</option></select></form></body>)

    val thrown = the [ConditionNotMetException] thrownBy { b.select(id, "2") }
    thrown.getMessage should equal("""> FAILED: select 'Id(select)' option '2' (not met within 2000 millis)""")
   }

}