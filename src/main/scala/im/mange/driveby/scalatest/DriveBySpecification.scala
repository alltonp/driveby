package im.mange.driveby.scalatest

import im.mange.driveby.tracking.{ExampleTracking, SpecificationTracking}
import org.scalatest.Suite

//TODO: with ErrorReporting
trait DriveBySpecification extends NakedSpecification with NakedExample with SpecificationTracking with ExampleTracking with DriveByReporting {
  this: Suite =>
}