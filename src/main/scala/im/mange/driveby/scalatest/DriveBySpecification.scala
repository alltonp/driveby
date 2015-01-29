package im.mange.driveby.scalatest

import org.scalatest.Spec
import im.mange.driveby.tracking.{ExampleTracking, SpecificationTracking}
import im.mange.driveby.BrowserAware

//TODO: with ErrorReporting
trait DriveBySpecification extends NakedSpecification with NakedExample with SpecificationTracking with ExampleTracking with DriveByReporting {
  this: Spec =>
}