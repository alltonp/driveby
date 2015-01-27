package im.yagni.driveby.tracking.report

import im.yagni.driveby.tracking.{Event, Format, SpecificationFinished, SpecificationStarted}

case class SpecificationRun(specificationId: Long, events: Seq[Event]) {
  import im.yagni.driveby.tracking.Format._

  private def started = events.filter(_.isInstanceOf[SpecificationStarted]).headOption
  private def finished = events.filter(_.isInstanceOf[SpecificationFinished]).headOption
  private def name = if (started.isDefined) started.get.asInstanceOf[SpecificationStarted].name

  def durationMillis = for { f <- finished; s <- started } yield f.at - s.at

  //TODO: show + or x instead and count examples per spec
  def report = " - " + millis(durationMillis) + " " + name
}
