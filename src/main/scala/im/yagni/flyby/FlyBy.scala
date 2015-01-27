package im.yagni.flyby

import im.yagni.driveby.{BrowserFactory, BrowserType}
import im.yagni.driveby.browser.ProbingBrowser
import im.yagni.common.{ConditionNotMetException, OnShutdown, Host}
import org.joda.time.DateTime

object FlyBy {
  import scala.concurrent.ops._

  def start() = {
    FlyServer
    new FlySpace(Host.name)
    //TODO: go back to hiding the space internally and have methods for add, remove, self-heal etc
  }

  //TODO: this looks a little off now
  def addBrowser(space: FlySpace, browserType: BrowserType) = {
    val agent = BrowserAgent(space, browserType)
    spawn { agent.start() }
    agent
  }
}