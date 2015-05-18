package im.mange.driveby.tracking.report

import im.mange.driveby.tracking.{Event, SpecificationFinished, SpecificationStarted}

case class SpecificationRun(specificationId: Long, events: Seq[Event]) {
  import im.mange.driveby.tracking.Format._

  private def started = events.find(_.isInstanceOf[SpecificationStarted])
  private def finished = events.find(_.isInstanceOf[SpecificationFinished])
  private def name = if (started.isDefined) started.get.asInstanceOf[SpecificationStarted].name

  def durationMillis = for { f <- finished; s <- started } yield f.at - s.at

  //TODO: show + or x instead and count examples per spec
  def report = " - " + millis(durationMillis) + " " + name
}
