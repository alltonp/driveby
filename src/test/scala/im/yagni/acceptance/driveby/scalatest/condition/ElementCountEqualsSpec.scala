package im.yagni.acceptance.driveby.scalatest.condition

import im.yagni.acceptance.driveby.scalatest.WebSpecification
import im.yagni.driveby.Class
import im.yagni.driveby.conditions._
import org.scalatest.Matchers

class ElementCountEqualsSpec extends WebSpecification with Matchers {
  def `count by class` {
    val clazz = Class("thing")
    given.page(<b class={clazz.className}>thing1</b> <b class={clazz.className}>thing2</b> <b class={clazz.className}>thing3</b>)
      .assert(ElementCountEquals(clazz, 3))
  }
}