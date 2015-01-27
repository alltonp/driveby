package im.yagni

import im.yagni.common.WebServerForServlets
import im.yagni.driveby.pool.{Application, ApplicationController}
import im.yagni.driveby.{BrowserTypes, DriveBy, DriveByConfig}

object WebSpecificationSuite {
  val basePort = 9000
  val instancesForParallel = 1

//  BrowserTypes.firefox.browserBinary = """./Applications/Firefox.app"""
  BrowserTypes.chrome.browserBinary = """./Applications/Google\ Chrome.app"""
  BrowserTypes.chrome.driverBinary = """./tools/chromedriver/chromedriver"""
  BrowserTypes.phantomjs.driverBinary = """./tools/phantomjs-1.9.8-macosx/bin/phantomjs"""

  DriveByConfig.waitTimeout = 2000
//  DriveByConfig.browserType = BrowserTypes.firefox // wont launch
//  DriveByConfig.browserType = BrowserTypes.phantomjs // launching but not running
  DriveByConfig.browserType = BrowserTypes.chrome  // launching and running not passing
  DriveByConfig.browserInstances = instancesForParallel

    //Noise on
//  DriveByConfig.reportAlways = true
//  DriveByConfig.trackingFullDump = true
//  DriveByConfig.trackingVerbose = true

//  DriveByConfig.flyHostname = Some("localhost")

  val PORT = basePort + 1
  val server = new WebServerForServlets(PORT, autoStart = false)

//  println(DriveBy.ping)

//  DriveByConfig.cleanupOnShutdown = true

//  DriveByConfig.applicationControllers = 1.to((instancesForParallel) - 1)
//    .map(n ⇒ InProcessApplicationController("Application " + BasePort + n, BasePort + n)).toList

  // Application's are not starting .. that seems to be the a problem ....

  val foo = new ApplicationController {
    val application = Application("App", PORT, java.net.InetAddress.getLocalHost.getHostName)

    beforeStart()
    val s = server

    def beforeStart() {}
    def start() { server.start() }
    def hasStarted = true
    def stop() { }
  }
  DriveByConfig.applicationControllers = List(foo)

  DriveBy.start()
}