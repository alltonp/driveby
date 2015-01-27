package im.yagni.acceptance.driveby.scalatest

object ThingsThatNeedTesting {
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
}
