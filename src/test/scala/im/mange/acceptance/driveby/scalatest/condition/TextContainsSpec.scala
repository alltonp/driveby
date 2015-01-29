package im.mange.acceptance.driveby.scalatest.condition

import im.mange.acceptance.driveby.scalatest.WebSpecification
import im.mange.driveby.Id
import im.mange.driveby.conditions._
import org.scalatest.Matchers

class TextContainsSpec extends WebSpecification with Matchers {
  def `work for an id` {
    val id = Id("contains")
    given.page(<b id={id.id}>foocontainsbar</b>)
      .assert(TextContains(id, "contains"))
  }
}