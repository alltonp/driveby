package im.yagni.flyby

import im.yagni.driveby.{BrowserFactory, BrowserType}
import im.yagni.driveby.browser.ProbingBrowser
import im.yagni.common.{ConditionNotMetException, Host}
import org.joda.time.DateTime

case class BrowserAgent(space: FlySpace, browserType: BrowserType, private var running: Boolean = true, private var id: Long = -1) {
  //TODO: clearly should be an option
  private var browser = BrowserFactory.create(browserType).asInstanceOf[ProbingBrowser]
  id = browser.id

  def browserId = id

  def start() {
    println("### Starting: " + this)

    space.write(RemoteBrowser(browserType.name.toString, browser.id, Host.name, free = true, mileage = 0, lastUsed = DateTime.now))

    while (running) {
      val toTakeTemplate = RemoteCommand(null, browser.id, executed = false, succeeded = false)
      val commandEntry = space.take(toTakeTemplate)

      commandEntry match {
        case Some(remoteCommand) => {
          try {
            browser.execute(remoteCommand.browserCommand)
            remoteCommand.succeeded = true
          }
          catch {
            case e: ConditionNotMetException => {
              remoteCommand.succeeded = false
              remoteCommand.exceptionMessage = e.getMessage
            }
            case e: Exception => {
              println("### an unexpected exception occurred: " + e)
              remoteCommand.succeeded = false; remoteCommand.exceptionMessage = e.getMessage
            }
          }
          finally {
            remoteCommand.executed = true
            space.write(remoteCommand)
          }
        }
        case None =>
      }
      Thread.`yield`()
    }

  }

  def stop() {
    println("### Stopping: " + this)
    //TODO: we should take the RemoteBrowser out
    //TODO: we should probably wait until the browser is not in  user (esp when mileage exceeded)
//    val r = space.read(RemoteBrowser(null, browser.id, null, null, null, null))
//    println(r)
    running = false
    browser.close()
  }
}
