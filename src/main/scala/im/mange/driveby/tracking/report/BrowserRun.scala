package im.mange.driveby.tracking.report

import im.mange.driveby.tracking.{BrowserCloseRequested, BrowserClosed, BrowserOpenRequested, BrowserOpened, BrowserTaken, BrowserWritten, _}

case class BrowserRun(browserId: Long, events: Seq[Event]) {
  import im.mange.driveby.tracking.Format._

  private def openRequested = events.find(_.isInstanceOf[BrowserOpenRequested])
  private def opened = events.find(_.isInstanceOf[BrowserOpened])
  private def didOpen = opened.isDefined
  private def closeRequested = events.find(_.isInstanceOf[BrowserCloseRequested])
  private def closed = events.find(_.isInstanceOf[BrowserClosed])
  private def didClose = closed.isDefined
  private def lastExampleFinished = events.filter(_.isInstanceOf[BrowserWritten]).reverse.headOption

  private def idleDuration = for { r <- closeRequested; l <- lastExampleFinished } yield r.at - l.at
  private def openingMillis = for { r <- opened; l <- openRequested } yield r.at - l.at
  private def closingMillis = for { c <- closed; r <- closeRequested } yield c.at - r.at

  //TODO: find a less shonky way to do this
  private def name = if (openRequested.isDefined) openRequested.get.asInstanceOf[BrowserOpenRequested].browserType else "???"
  private def exampleCount = events.count(_.isInstanceOf[BrowserTaken])

  //TODO: should be N/A for idle and examples if did not open and close
  def report = "- " + browserId +
    " (" + name + ")" +
    " open: " + (if (didOpen) millis(openingMillis) else " Fail") +
    ", close: " + (if (didClose) millis(closingMillis) else " Fail") +
    ", idle: " + millis(idleDuration) +
    ", examples: " + count(exampleCount)
}
