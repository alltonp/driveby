package im.yagni.driveby.specs2

import im.yagni.driveby.tracking.TrackingIds
import im.yagni.driveby.{ExampleAware, Example}
import org.specs2.specification.AroundOutside
import org.specs2.execute.{AsResult, DecoratedResultException, FailureException, Result}
import org.specs2.mutable.SpecificationFeatures

trait NakedExample[T] extends AroundOutside[T] with SpecificationFeatures with ExampleAware {
  //TODO: get example name working again
  val example = Example("xxx", TrackingIds.nextExampleId)

  //this is weird, but specs2 1.12.x seems to require it
  def content = fragments

  def around[R : AsResult](testBody: =>R): Result = {
    try {
      doBeforeExample()
      //TODO: should pass the example through to the body somehow
      val r = AsResult(testBody)
      if (!r.isSuccess && !r.isSkipped) doOnFailure(r.message)
      r
    }
    catch {
      case e: FailureException => doOnFailure(e.f.exception.getMessage); throw e
      case e: DecoratedResultException => throw e //work around specs2 DataTable throwing Exception for 'success' - sigh
      case e: Exception => doOnFailure(e.getMessage); throw e
    }
    finally {
      doAfterExample()
    }
  }
}

