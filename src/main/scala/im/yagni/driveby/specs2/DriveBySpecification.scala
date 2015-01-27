package im.yagni.driveby.specs2

import im.yagni.driveby.tracking.{SpecificationTracking, ExampleTracking}
import im.yagni.driveby.{BrowserAware, SpecificationAware}
import im.yagni.driveby.report.Reporting

//TODO: SpecificationTracking looks a bit odd ... SpecificationAware?
trait DriveBySpecification extends NakedSpecification with SpecificationTracking {
  self: SpecificationTracking =>

  //TODO: not sure if BrowserAware should be here or not ... it looks wrong but it's currently needed by Reporting - maybe it should be in the clients Specification
  //TODO: maybe Reporting should have the self type of BrowserAware
  trait DriveByExample[T] extends NakedExample[T] with SpecificationAware with ExampleTracking with BrowserAware with Reporting {
    def specification = self.specification
  }
}