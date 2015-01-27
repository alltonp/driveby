package im.yagni.acceptance.driveby.scalatest

import im.yagni.driveby.Id
import im.yagni.driveby.conditions._
import org.scalatest.Matchers

class ElementHiddenSpec extends WebSpecification with Matchers {
  def `work for id` {
    val id = Id("hidden")
    given.page(<b id={id.id} style="display:none">hidden</b>)
      .assert(ElementHidden(id))
  }
}