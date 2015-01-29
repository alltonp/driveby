package im.mange.acceptance.driveby.scalatest.condition

import im.mange.acceptance.driveby.scalatest.WebSpecification
import im.mange.driveby.Id
import im.mange.driveby.conditions._
import org.scalatest.Matchers

class ElementHiddenSpec extends WebSpecification with Matchers {
  def `work for id` {
    val id = Id("hidden")
    given.page(<b id={id.id} style="display:none">hidden</b>)
      .assert(ElementHidden(id))
  }
}