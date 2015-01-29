package im.mange.driveby.scalatest

import im.mange.driveby.{Example, ExampleAware}
import im.mange.driveby.tracking.TrackingIds
import org.scalatest._

trait NakedExample extends SuiteMixin with BeforeAndAfterEach with ExampleAware {
  this: SpecLike =>

  private var capturedExample: Option[Example] = None

  //TODO: alternatively we could use this.testNames to build a list of examples here ...
  def example = capturedExample.getOrElse(throw new RuntimeException("Spooky I'm running, yet at the same time I don't exist"))

  override def beforeEach() { doBeforeExample() }
  override def afterEach() { doAfterExample() }

  abstract protected override def runTest(testName: String, args: Args): Status = {
    capturedExample = Some(Example(testName, TrackingIds.nextExampleId))
    super.runTest(testName, args)
  }

  abstract override def withFixture(test: NoArgTest): Outcome = {
    super.withFixture(test) match {
      case f: Failed => { doOnFailure("Failure: " + f.exception.getMessage); f }
      case other => other
    }
  }
}
