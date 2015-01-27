package im.yagni.driveby.specs2

import im.yagni.driveby.tracking.TrackingIds
import im.yagni.driveby.{SpecificationAware, Specification}
import org.specs2.specification.{Step, Fragments}
import org.specs2.mutable.SpecificationWithJUnit

trait NakedSpecification extends SpecificationWithJUnit with SpecificationAware {
  val specification = Specification(this.getClass.getName, TrackingIds.nextSpecificationId)

  override def map(fs: => Fragments) = Step(doBeforeSpecification()) ^ fs ^ Step(doAfterSpecification())
}