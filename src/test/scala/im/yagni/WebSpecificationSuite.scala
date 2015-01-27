package im.yagni

import im.yagni.common.WebServerForServlets
import im.yagni.driveby.{BrowserTypes, DriveBy, DriveByConfig}

object WebSpecificationSuite {
  val basePort = 9000
  val instancesForParallel = 1

//  BrowserTypes.firefox.browserBinary = """./Applications/Firefox.app"""
  BrowserTypes.chrome.browserBinary = """./Applications/Google\ Chrome.app"""
  BrowserTypes.chrome.driverBinary = """./tools/chromedriver/chromedriver"""
  BrowserTypes.phantomjs.driverBinary = """./tools/phantomjs-1.9.7-macosx/bin/phantomjs"""

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
  val server = new WebServerForServlets(PORT)

//  println(DriveBy.ping)

//  DriveByConfig.cleanupOnShutdown = true

//  DriveByConfig.applicationControllers = 1.to((instancesForParallel) - 1)
//    .map(n ⇒ InProcessApplicationController("Application " + BasePort + n, BasePort + n)).toList

  DriveBy.start()
}