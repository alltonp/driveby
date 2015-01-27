package im.yagni.driveby

trait SpecificationAware {
  def specification: Specification

  //TODO: why arent these List[Function1[Specification, Unit]]?
  def beforeSpecification: List[Function0[Unit]] = Nil
  def afterSpecification: List[Function0[Unit]] = Nil

  final def doBeforeSpecification() { beforeSpecification.reverse.foreach(_()) }
  final def doAfterSpecification() { afterSpecification.foreach(_()) }
}