package im.mange.driveby.tracking

import im.mange.driveby.{ExampleAware, SpecificationAware, Example}

trait ExampleTracking extends ExampleAware {
  self: SpecificationAware =>

  override def beforeExample = exampleStarted _ :: super.beforeExample
  override def afterExample = exampleFinished _ :: super.afterExample
  override def onFailure = exampleFailed _ :: super.onFailure

  private def exampleStarted(example: Example) { Tracker.add(ExampleStarted(specification, example)) }
  private def exampleFinished(example: Example) { Tracker.add(ExampleFinished(specification, example))}
  private def exampleFailed(example: Example, message: String) { Tracker.add(ExampleFailed(specification, example))}
}



