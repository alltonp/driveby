package im.yagni.acceptance.driveby.specs2

import im.yagni.acceptance.driveby.driver.BrowserDriver
import im.yagni.driveby.Id
import im.yagni.driveby.conditions._

class BrowserSpec extends WebSpecification {

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