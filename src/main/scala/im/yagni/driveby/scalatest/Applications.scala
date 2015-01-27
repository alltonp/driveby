package im.yagni.driveby.scalatest

import im.yagni.driveby.pool.PooledApplications

//TODO: pull out error messages somewhere
trait Applications extends PooledApplications {
//TODO: can we pull this up into PooledApplications
  def application = pooledApplication.getOrElse(throw new RuntimeException("Failed to acquire an application for this example within the timeout"))
}
