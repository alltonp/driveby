package im.mange.driveby.tracking

import im.mange.driveby.SpecificationAware

trait SpecificationTracking extends SpecificationAware {
  override def beforeSpecification = specificationStarted _ :: super.beforeSpecification
  override def afterSpecification = specificationFinished _ :: super.afterSpecification

  private def specificationStarted() { Tracker.add(SpecificationStarted(specification)) }
  private def specificationFinished() { Tracker.add(SpecificationFinished(specification)) }
}