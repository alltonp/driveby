package im.yagni.driveby.scalatest

import org.scalatest.Spec
import im.yagni.driveby.tracking.{ExampleTracking, SpecificationTracking}
import im.yagni.driveby.BrowserAware

//TODO: with ErrorReporting
trait DriveBySpecification extends NakedSpecification with NakedExample with SpecificationTracking with ExampleTracking with DriveByReporting {
  this: Spec =>
}