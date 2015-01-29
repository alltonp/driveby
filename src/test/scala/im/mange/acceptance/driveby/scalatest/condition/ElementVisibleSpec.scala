package im.mange.acceptance.driveby.scalatest.condition

import im.mange.acceptance.driveby.scalatest.WebSpecification
import im.mange.driveby.Id
import im.mange.driveby.conditions._
import org.scalatest.Matchers

class ElementVisibleSpec extends WebSpecification with Matchers {
  def `work for id` {
    val id = Id("visible")
    given.page(<b id={id.id} style="display:block">visible</b>)
      .assert(ElementVisible(id))
  }
}