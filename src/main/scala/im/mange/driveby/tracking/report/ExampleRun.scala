package im.mange.driveby.tracking.report

import im.mange.driveby.DriveByConfig
import im.mange.driveby.commands.Assert
import im.mange.driveby.tracking._

case class ExampleRun(exampleId: Long, events: Seq[Event]) {
  import im.mange.driveby.tracking.Format._

  //TODO: de-dupe
  private val applicationRequested = events.filter(_.isInstanceOf[ApplicationTakeRequested]).headOption
  private val applicationTaken = events.filter(_.isInstanceOf[ApplicationTaken]).headOption
  private val applicationWritten = events.filter(_.isInstanceOf[ApplicationWritten]).headOption
  private val browserRequested = events.filter(_.isInstanceOf[BrowserTakeRequested]).headOption
  private val browserTaken = events.filter(_.isInstanceOf[BrowserTaken]).headOption
  private val browserWritten = events.filter(_.isInstanceOf[BrowserWritten]).headOption
  private val finish = events.filter(_.isInstanceOf[ExampleFinished]).headOption
  private val start = events.filter(_.isInstanceOf[ExampleStarted]).headOption

  private def assertionCount = browserCommands.filter(_.isInstanceOf[Assert]).size
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
    count(exampleId) + ":"
    name
}
