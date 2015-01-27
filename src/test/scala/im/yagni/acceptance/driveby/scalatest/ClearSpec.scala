package im.yagni.acceptance.driveby.scalatest

import im.yagni.driveby.Id
import im.yagni.driveby.conditions.ValueEquals
import org.scalatest.Matchers

class ClearSpec extends WebSpecification with Matchers {
  def `clear text in a textbox` {
    val id = Id("textBox")
    given.page(<body><form><input type="text" id={id.id}/></form></body>)
      .enter(id, "text")
      .clear(id)
      .assert(ValueEquals(id, ""))
   }
}