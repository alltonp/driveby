package im.yagni.acceptance.driveby.scalatest.browser

import im.yagni.acceptance.driveby.scalatest.WebSpecification
import im.yagni.common.ConditionNotMetException
import im.yagni.driveby.Id
import im.yagni.driveby.conditions._
import org.scalatest.Matchers

class ClickSpec extends WebSpecification with Matchers {
  def `click must click a link` {
    val id = Id("link")
    val link = "next"

    //TODO: this is a bit nasty
    //given.addPage("/" + example.id + "/" + link, <title>linked page</title>)

    given.addPage("/" + link, <title>linked page</title>)
      .page(<a href={link} id={id.id}>{link}</a>)
      .click(id)
      .assert(TitleEquals("linked page"))
  }

  def `click must only click if element can be uniquely identified` {
    val id = Id("link")
    val link = "next"

    val b = given.page(<a href={link} id={id.id}>{link}</a><br/><a href={link} id={id.id}>{link}</a>)

    val thrown = the [ConditionNotMetException] thrownBy { b.click(id) }
    thrown.getMessage should equal("""> FAILED: for: Id(link) expected 1 element but was "2" (not met within 2000 millis)""")
  }

  def `click must only click if element is interactable` {
    val id = Id("link")
    val link = "next"

    val b = given.page(<a href={link} id={id.id} style="display: none">{link}</a>)

    val thrown = the [ConditionNotMetException] thrownBy { b.click(id) }
    thrown.getMessage should equal("""> FAILED: for: Id(link) expected 1 element but was "0" (not met within 2000 millis)""")
  }
}