package im.mange.driveby.tracking.report

import im.mange.driveby.tracking._

case class ApplicationRun(applicationId: Long, events: Seq[Event]) {
  import im.mange.driveby.tracking.Format._

  private def startRequested = events.find(_.isInstanceOf[ApplicationStartRequested])
  private def started = events.find(_.isInstanceOf[ApplicationStarted])
  private def didStart = started.isDefined
  private def stopRequested = events.find(_.isInstanceOf[ApplicationStopRequested])
  private def stopped = events.find(_.isInstanceOf[ApplicationStopped])
  private def didStop = stopped.isDefined
  private def lastExampleFinished = events.filter(_.isInstanceOf[ApplicationWritten]).reverse.headOption

  private def idleMillis = for { r <- stopRequested; l <- lastExampleFinished } yield r.at - l.at
  private def startingMillis = for { s <- started; r <- startRequested } yield s.at - r.at
  private def stoppingMillis = for { s <- stopped; r <- stopRequested } yield s.at - r.at
  //TODO: find a less shonky way to do this
  private def name = if (startRequested.isDefined) startRequested.get.asInstanceOf[ApplicationStartRequested].applicationType else "???"
  private def exampleCount = events.count(_.isInstanceOf[ApplicationTaken])

  //TODO: should be N/A for idle and examples if did not start and stop
  def report = "- " + applicationId +
    " (" + name + ")" +
    " start: " + (if (didStart) millis(startingMillis) else "Failed") +
    ", stop: " + (if (didStop) millis(stoppingMillis) else "Failed") +
    ", idle: " + millis(idleMillis) +
    ", examples: " + count(exampleCount)
}
