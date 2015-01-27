package im.yagni.acceptance.driveby.specs2

import im.yagni.common.ConditionNotMetException
import im.yagni.driveby.Id
import im.yagni.driveby.conditions._
import im.yagni.acceptance.driveby.driver.BrowserDriver

//TODO: split each BrowserCommand out into a separate spec

class BrowserSpec extends WebSpecification {

  "select" should {

    "only select if element can be uniquely identified" in rawBrowser {
      (given: BrowserDriver) => {
        val id = Id("select")
        given.page(<body><form><select id={id.id}/><br/><select id={id.id}/></form></body>)
          .select(id, "blah") must throwA(new ConditionNotMetException("""> FAILED: select 'Id(select)' option 'blah' (not met within 2000 millis)"""))
      }
    }

    "only select if element is interactable" in rawBrowser {
      (given: BrowserDriver) => {
        val id = Id("select")
        given.page(<body><form><select id={id.id} style="display: none"/></form></body>)
          .select(id, "blah") must throwA(new ConditionNotMetException("""> FAILED: select 'Id(select)' option 'blah' (not met within 2000 millis)"""))
      }
    }

    "only select if option is present" in rawBrowser {
      (given: BrowserDriver) => {
        val id = Id("select")
        given.page(<body><form><select id={id.id}><option>1</option></select></form></body>)
          .select(id, "2") must throwA(new ConditionNotMetException("""> FAILED: select 'Id(select)' option '2' (not met within 2000 millis)"""))
      }
    }
  }

  "html" should {
    "be the page html" in rawBrowser {
      (given: BrowserDriver) => {
        val html = <body>html</body>
        given.page(html)
          .html must contain(html.toString())
      }
    }
  }

  "clear" should {
    "clear text in a textbox" in rawBrowser {
      (given: BrowserDriver) => {
        val id = Id("textBox")
        given.page(<body><form><input type="text" id={id.id}/></form></body>)
          .enter(id, "text")
          .clear(id)
          .assert(ValueEquals(id, ""))
      }
    }
  }
}