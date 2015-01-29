package im.mange.driveby.pool

import im.mange.driveby.{ApplicationAware, ExampleAware, Example}

//TODO: the PooledBrowsers seems very odd here, should be able to be separate
trait PooledApplications extends ExampleAware with PooledBrowsers with ApplicationAware {
  var pooledApplication: Option[Application] = None

  override def beforeExample = takeApplication _ :: super.beforeExample
  override def afterExample = writeApplication _ :: super.afterExample

  private def takeApplication(example: Example) {
    pooledBrowser match {
      case Some(_) => pooledApplication = ApplicationPool.take(example)
      case None => throw new RuntimeException("Already failed to acquire a browser for this example within the timeout, so didnt bother trying to get an application")
    }
  }

  private def writeApplication(example: Example) { ApplicationPool.write(pooledApplication, example) }
}