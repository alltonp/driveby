package im.yagni.driveby

trait ExampleAware {
  def example: Example

  def beforeExample: List[Function1[Example, Unit]] = Nil
  def afterExample: List[Function1[Example, Unit]] = Nil
  def onFailure: List[Function2[Example, String, Unit]] = Nil

  final def doBeforeExample() { beforeExample.reverse.foreach(_(example)) }
  final def doAfterExample() { afterExample.foreach(_(example)) }
  final def doOnFailure(message: String) { onFailure.foreach(_(example, message)) }
}