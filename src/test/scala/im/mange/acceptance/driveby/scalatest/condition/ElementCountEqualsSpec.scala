package im.mange.acceptance.driveby.scalatest.condition

import im.mange.acceptance.driveby.scalatest.WebSpecification
import im.mange.driveby.Class
import im.mange.driveby.conditions._
import org.scalatest.Matchers

class ElementCountEqualsSpec extends WebSpecification with Matchers {
  def `count by class` {
    val clazz = Class("thing")
    given.page(<b class={clazz.className}>thing1</b> <b class={clazz.className}>thing2</b> <b class={clazz.className}>thing3</b>)
      .assert(ElementCountEquals(clazz, 3))
  }
}