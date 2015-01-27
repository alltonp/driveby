package im.yagni.acceptance.driveby.scalatest

import im.yagni.driveby.Id
import im.yagni.driveby.conditions._
import org.scalatest.Matchers

class ElementVisibleSpec extends WebSpecification with Matchers {
  def `work for id` {
    val id = Id("visible")
    given.page(<b id={id.id} style="display:block">visible</b>)
      .assert(ElementVisible(id))
  }
}