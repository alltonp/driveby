package im.mange.driveby.scalatest

import im.mange.driveby.pool.PooledBrowsers

trait Browsers extends PooledBrowsers {
  //TODO: can we pull this up into PooledBrowsers
  def browser = pooledBrowser.getOrElse(throw new RuntimeException("Failed to acquire a browser for this example within the timeout"))
}