package im.yagni.driveby.scalatest

import org.scalatest.{Spec, BeforeAndAfter}
import im.yagni.driveby.{SpecificationAware, Specification}
import im.yagni.driveby.tracking.TrackingIds

trait NakedSpecification extends Spec with BeforeAndAfter with SpecificationAware {
  this: Spec =>

  val specification = Specification(this.getClass.getName, TrackingIds.nextSpecificationId)

  before { doBeforeSpecification() }
  after { doAfterSpecification() }
}
