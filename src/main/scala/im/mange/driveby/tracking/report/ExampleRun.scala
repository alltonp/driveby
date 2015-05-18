package im.mange.driveby.tracking.report

import im.mange.driveby.DriveByConfig
import im.mange.driveby.commands.Assert
import im.mange.driveby.tracking._

case class ExampleRun(exampleId: Long, events: Seq[Event]) {
  import im.mange.driveby.tracking.Format._

  //TODO: de-dupe
  private val applicationRequested = events.find(_.isInstanceOf[ApplicationTakeRequested])
  private val applicationTaken = events.find(_.isInstanceOf[ApplicationTaken])
  private val applicationWritten = events.find(_.isInstanceOf[ApplicationWritten])
  private val browserRequested = events.find(_.isInstanceOf[BrowserTakeRequested])
  private val browserTaken = events.find(_.isInstanceOf[BrowserTaken])
  private val browserWritten = events.find(_.isInstanceOf[BrowserWritten])
  private val finish = events.find(_.isInstanceOf[ExampleFinished])
  private val start = events.find(_.isInstanceOf[ExampleStarted])

  private def assertionCount = browserCommands.count(_.isInstanceOf[Assert])
  private def applicationWaitMillis = for { t <- applicationTaken; r <- applicationRequested } yield t.at - r.at
  private def browserCommands = events.filter(_.isInstanceOf[BrowserCommandExecuted]).map(_.asInstanceOf[BrowserCommandExecuted].command)
  private def browserCommandsCount = browserCommands.size
  private def browserWaitMillis = for { t <- browserTaken; r <- browserRequested } yield t.at - r.at
  private def name = if (start.isDefined) start.get.asInstanceOf[ExampleStarted].name else "?"
  private def result = if (!events.filter(_.isInstanceOf[ExampleFailed]).isEmpty) "x" else "+"
  private def totalMillis = for { f <- finish; s <- start } yield f.at - s.at

  //TODO: de-shonk me - I just don't know how yet ....
  def exampleOnlyMillis = {
    val startAt = if (applicationTaken.isDefined) applicationTaken.get else browserTaken.get
    val finishAt = if (applicationWritten.isDefined) applicationWritten.get else browserWritten.get
    finishAt.at - startAt.at
  }

  //TODO: add the browserId and exampleId so we can see what's going on
  //TODO: mkString this stuff up
  //TODO: we should do the same check for browserControllers on the 'browser'
  def report = " " + result + " " + millis(Some(exampleOnlyMillis)) +
    " (browser: " + millis(browserWaitMillis) +
    ", app: " + (if (DriveByConfig.applicationControllers == Nil) "N/A" else millis(applicationWaitMillis)) +
    ", total: " + millis(totalMillis) +
    ", commands: " + count(browserCommandsCount) +
    ", asserts: " + count(assertionCount) + ") " +
    count(exampleId) + ": " +
    name
}
