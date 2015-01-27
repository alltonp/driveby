package im.yagni.driveby.tracking.report

import im.yagni.driveby.tracking._

case class ApplicationRun(applicationId: Long, events: Seq[Event]) {
  import im.yagni.driveby.tracking.Format._

  private def startRequested = events.filter(_.isInstanceOf[ApplicationStartRequested]).headOption
  private def started = events.filter(_.isInstanceOf[ApplicationStarted]).headOption
  private def didStart = started.isDefined
  private def stopRequested = events.filter(_.isInstanceOf[ApplicationStopRequested]).headOption
  private def stopped = events.filter(_.isInstanceOf[ApplicationStopped]).headOption
  private def didStop = stopped.isDefined
  private def lastExampleFinished = events.filter(_.isInstanceOf[ApplicationWritten]).reverse.headOption

  private def idleMillis = for { r <- stopRequested; l <- lastExampleFinished } yield r.at - l.at
  private def startingMillis = for { s <- started; r <- startRequested } yield s.at - r.at
  private def stoppingMillis = for { s <- stopped; r <- stopRequested } yield s.at - r.at
  //TODO: find a less shonky way to do this
  private def name = if (startRequested.isDefined) startRequested.get.asInstanceOf[ApplicationStartRequested].applicationType else "???"
  private def exampleCount = events.filter(_.isInstanceOf[ApplicationTaken]).size

  //TODO: should be N/A for idle and examples if did not start and stop
  def report = "- " + applicationId +
    " (" + name + ")" +
    " start: " + (if (didStart) millis(startingMillis) else "Failed") +
    ", stop: " + (if (didStop) millis(stoppingMillis) else "Failed") +
    ", idle: " + millis(idleMillis) +
    ", examples: " + count(exampleCount)
}
