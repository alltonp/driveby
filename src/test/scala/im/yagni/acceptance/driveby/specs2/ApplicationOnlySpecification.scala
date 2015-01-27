package im.yagni.acceptance.driveby.specs2

import im.yagni.driveby.specs2.{Applications, DriveBySpecification}
import im.yagni.driveby.pool.Application

case class ApplicationOnlyDriver(application: Application)

//TODO: make it possible to do this .. we cant right now because PooledApplications depends on PooledBrowsers
//trait ApplicationOnlySpecification extends DriveBySpecification with Applications {
//  trait WebExample[T] extends DriveByExample[T] with Applications
//
//  def driver = new WebExample[ApplicationOnlyDriver] {
//    def outside: ApplicationOnlyDriver = new ApplicationOnlyDriver(application)
//  }
//}
