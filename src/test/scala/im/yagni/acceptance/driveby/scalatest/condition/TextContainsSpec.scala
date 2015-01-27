package im.yagni.acceptance.driveby.scalatest.condition

import im.yagni.acceptance.driveby.scalatest.WebSpecification
import im.yagni.driveby.Id
import im.yagni.driveby.conditions._
import org.scalatest.Matchers

class TextContainsSpec extends WebSpecification with Matchers {
  def `work for an id` {
    val id = Id("contains")
    given.page(<b id={id.id}>foocontainsbar</b>)
      .assert(TextContains(id, "contains"))
  }
}