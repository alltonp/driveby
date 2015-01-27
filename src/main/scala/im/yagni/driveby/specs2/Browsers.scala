package im.yagni.driveby.specs2

import im.yagni.driveby.pool.PooledBrowsers

//TODO: pull out error messages somewhere
trait Browsers {
  trait Browsers extends PooledBrowsers {
    //TODO: can we pull this up into PooledBrowsers
    def browser = pooledBrowser.getOrElse(throw new RuntimeException("Failed to acquire a browser for this example within the timeout"))
  }
}