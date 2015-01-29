package im.mange.flyby

import im.mange.driveby.{BrowserFactory, BrowserType, DriveByConfig}
import im.mange.common.Times._
import scala.Some
import im.mange.driveby.browser.ProbingBrowser
import im.mange.common.{OnShutdown, Host, ConditionNotMetException}
import org.joda.time.DateTime

//TODO: use -D to set DriveByConfig.flyBinary etc (as we will probably make this an executable jar, so assuming no args)
//TODO: definitely rename to DriveByServer

object FlyByAgent extends App {
  try {
    //TODO: eventually kill this ...
//    DriveByConfig.trackingVerbose = true
    //TODO: actually, to avoid memory leaks we really want ...
    //DriveByConfig.trackingEnabled = false
    //TODO: eventually this will be from the -D
    DriveByConfig.flyBinary = Some("bin/fly/bin/MacOSX/i386/fly")
    //TODO: this is well iffy ..
    DriveByConfig.waitTimeout = 2000

    //TODO: e.g. 3*chrome, 2*firefox etc ...
    //TODO: specify these ... possibly pass through to start and call multiple times for different configs
    //DriveByConfig.browserInstances
    //DriveByConfig.browserType

    //TODO: get rid of annoying log4j sys err messages
    val space = FlyBy.start()

    var browsers = List[BrowserAgent]()

    times(DriveByConfig.browserInstances) {
      val browser = FlyBy.addBrowser(space, DriveByConfig.browserType)
      browsers = browser :: browsers
      OnShutdown.execute("Close browser " + browser, browser.stop _)
    }

//    println("### browsers:" + browsers)
//
//    Thread.sleep(10000)
//
//    browsers.foreach(_.stop())
//
//    println("### browsers:" + browsers)

//    System.exit(0)

  }
  catch {
    case e: Exception => println(e.getMessage); System.exit(-1)
  }
}