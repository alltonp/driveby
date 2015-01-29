package im.mange.driveby

import pool.ApplicationController

//TODO: please make me non-static
object DriveByConfig {
  var applicationControllers: Seq[ApplicationController] = Nil
  var applicationStartUpWaitTimeout = 60000
  var applicationTakeWaitTimeout = 30000
  var browserInstances = Runtime.getRuntime.availableProcessors
  var browserType: BrowserType = BrowserTypes.firefox
  var browserTakeWaitTimeout = 60000
  var browsersMaximised = true
  var cleanupOnShutdown = true
  var flyHostname: Option[String] = None
  var flyBinary: Option[String] = None
  var flySpaceDefaultTimeoutMillis: Long = Int.MaxValue
  var flySpaceDefaultMatchLimit: Long = 1000
  var outputDir = "target/driveby/"
  var reportAlways = false
  var trackingEnabled = true
  var trackingFullDump = false
  var trackingVerbose = false
  var waitPollPeriod = 1
  var waitTimeout = 5000


  //TODO:
  //flyport) = default
  //browserWaitTimeout = 60000
  //browsersKeepOpen .... just sets up a localhost fly ...

  //TODO: to give this a go properly
  //fly bin property required and checked
  //all other fly bits to be checked in/emails
  //DriveBy.start() - needs to create the right kind of pool
  //this can all be done on the air ....
  //move all spiky stuff out of the way
  //what happens to tracking? - currently it occurs on the wrong side ... browser.execute ... make it should be in the executor...
  //... actually it might be just right
  //tracking of browser/open/close will be odd ...
  //open/close need to be in BrowserPool trait
}
