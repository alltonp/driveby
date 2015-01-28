package im.yagni.flyby

import im.yagni.common.Host
import im.yagni.driveby.BrowserType

import scala.concurrent.Future

object FlyBy {
  import scala.concurrent.ExecutionContext.Implicits.global

  def start() = {
    FlyServer
    new FlySpace(Host.name)
    //TODO: go back to hiding the space internally and have methods for add, remove, self-heal etc
  }

  //TODO: this looks a little off now
  def addBrowser(space: FlySpace, browserType: BrowserType) = {
    val agent = BrowserAgent(space, browserType)
    Future { agent.start() }
    agent
  }
}