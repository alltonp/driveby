package im.yagni.acceptance.driveby.specs2

import im.yagni.acceptance.driveby.driver.BrowserDriver
import im.yagni.common.ConditionNotMetException
import im.yagni.driveby.Id
import im.yagni.driveby.conditions._

class ConditionsSpec extends WebSpecification {
  //TODO: test missing conditions
  //TODO: test all sad paths
  //TODO: test probing (via delays somehow)
  //TODO: find out why some messages have an extra " " before butWas - is it Assert(message)?
  //TODO: find out why some messages have an extra " " before (not met within ...) - is it Assert(message)?
  //TODO: review/improve all messages
  //TODO: split out into one spec per condition

  //TODO: these next two shouldnt be here ...
  //TODO: need to make this work in a nicer way
  //  def `current url must be the page url` {
  //    given.page(<html/>)
  //            .assert(CurrentUrlEquals("foo"))
  //  }

  //  TODO: need to make this work in a nicer way
  //  def `current url must contain the expected` {
  //    given.page(<html/>)
  //      .assert(CurrentUrlContains("foo"))
  //  }

  //TODO: have a spec ...
//  "SelectOptionsEquals" should {
//    "pass happy path" in rawBrowser {
//      (given: BrowserDriver) => {
//        val id = Id("TextEmpty")
//        given.page(<b id={id.id}></b>)
//          .assert(TextEmpty(id))
//      }
//    }
//  }

  "TextEmpty" should {
    "pass for id" in rawBrowser {
      (given: BrowserDriver) => {
        val id = Id("TextEmpty")
        given.page(<b id={id.id}></b>)
          .assert(TextEmpty(id))
      }
    }

    "pass for id with children" in rawBrowser {
      (given: BrowserDriver) => {
        val id = Id("TextEmptyWithChild")
        given.page(<b id={id.id}><div></div></b>)
          .assert(TextEmpty(id))
      }
    }

    "fail for id with text" in rawBrowser {
      (given: BrowserDriver) => {
        val id = Id("TextEmptyWithText")
        given.page(<b id={id.id}>text</b>)
          .assert(TextEmpty(id)) must throwA(new ConditionNotMetException(
          """> FAILED: Assert TextEmpty("Id(TextEmptyWithText)", "true") but was "text"  (not met within 2000 millis)"""))
      }
    }

    "fail for id with children with text" in rawBrowser {
      (given: BrowserDriver) => {
        val id = Id("TextEmptyWithChildWithText")
        given.page(<b id={id.id}><div>childtext</div></b>)
          .assert(TextEmpty(id)) must throwA(new ConditionNotMetException(
          """> FAILED: Assert TextEmpty("Id(TextEmptyWithChildWithText)", "true") but was "childtext"  (not met within 2000 millis)"""))
      }
    }
  }

  "ElementEmpty" should {
    "pass for id when no children and no text" in rawBrowser {
      (given: BrowserDriver) => {
        val id = Id("ElementEmpty")
        given.page(<b id={id.id}></b>)
          .assert(ElementEmpty(id))
       }
    }

    "fail for id with children" in rawBrowser {
      (given: BrowserDriver) => {
        val id = Id("ElementEmptyWithChild")
        given.page(<b id={id.id}><div></div></b>)
          .assert(ElementEmpty(id)) must throwA(new ConditionNotMetException(
          """> FAILED: Assert ElementEmpty("Id(ElementEmptyWithChild)", "true") but was "Element has 1 children and "" text"  (not met within 2000 millis)"""))
      }
    }

    "fail for id with text" in rawBrowser {
      (given: BrowserDriver) => {
        val id = Id("ElementEmptyWithText")
        given.page(<b id={id.id}>textInElement</b>)
          .assert(ElementEmpty(id)) must throwA(new ConditionNotMetException(
          """> FAILED: Assert ElementEmpty("Id(ElementEmptyWithText)", "true") but was "Element has 0 children and "textInElement" text"  (not met within 2000 millis)"""))
      }
    }
  }

}