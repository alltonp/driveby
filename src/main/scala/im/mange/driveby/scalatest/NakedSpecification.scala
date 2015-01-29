package im.mange.driveby.scalatest

import org.scalatest.{Spec, BeforeAndAfter}
import im.mange.driveby.{SpecificationAware, Specification}
import im.mange.driveby.tracking.TrackingIds

trait NakedSpecification extends Spec with BeforeAndAfter with SpecificationAware {
  this: Spec =>

  val specification = Specification(this.getClass.getName, TrackingIds.nextSpecificationId)

  before { doBeforeSpecification() }
  after { doAfterSpecification() }
}
