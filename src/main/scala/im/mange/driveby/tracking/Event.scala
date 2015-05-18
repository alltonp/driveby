package im.mange.driveby.tracking

import im.mange.driveby.{BrowserCommand, Example, Specification}

trait Event {
  val at = System.currentTimeMillis()
  //TODO: this blows, use traits to determine types
  val exampleId = -1L
  val browserId = -1L
  val applicationId = -1L
  val specificationId = -1L
}

case class BrowserOpenRequested(browserType: String, override val browserId: Long) extends Event
case class BrowserOpened(browserType: String, override val browserId: Long) extends Event
case class BrowserOpenFailed(browserType: String) extends Event
case class BrowserCommandExecuted(command: BrowserCommand, override val exampleId: Long) extends Event
case class BrowserCloseRequested(override val browserId: Long) extends Event
case class BrowserClosed(override val browserId: Long) extends Event
case class BrowserCloseFailed(override val browserId: Long) extends Event
case class BrowserWritten(override val exampleId: Long, override val browserId: Long) extends Event
case class BrowserTaken(override val exampleId: Long, override val browserId: Long) extends Event
case class BrowserTakeRequested(override val exampleId: Long) extends Event
case class BrowserTakeTimeout(override val exampleId: Long) extends Event

case class ApplicationStartRequested(applicationType: String, override val applicationId: Long) extends Event
case class ApplicationStarted(applicationType: String, override val applicationId: Long) extends Event
case class ApplicationStartFailed(applicationType: String, override val applicationId: Long) extends Event
case class ApplicationStopRequested(override val applicationId: Long) extends Event
case class ApplicationStopped(override val applicationId: Long) extends Event
case class ApplicationStopFailed(override val applicationId: Long) extends Event
case class ApplicationWritten(override val exampleId: Long, override val applicationId: Long) extends Event
case class ApplicationTaken(override val exampleId: Long, override val applicationId: Long) extends Event
case class ApplicationTakeRequested(override val exampleId: Long) extends Event
case class ApplicationTakeTimeout(override val exampleId: Long) extends Event

case class ExampleStarted(specification: Specification, example: Example) extends Event {
  def name = specification.name + "." + example.description
  override val exampleId = example.id
}

case class ExampleFinished(specification: Specification, example: Example) extends Event {
  def name = specification.name + "." + example.description
  override val exampleId = example.id
}

case class ExampleFailed(specification: Specification, example: Example) extends Event {
  def name = specification.name + "." + example.description
  override val exampleId = example.id
}

case class Info(message: String, override val exampleId: Long) extends Event

case class SpecificationStarted(specification: Specification) extends Event {
  def name = specification.name
  override val specificationId = specification.id
}
case class SpecificationFinished(specification: Specification) extends Event {
  override val specificationId = specification.id
}

