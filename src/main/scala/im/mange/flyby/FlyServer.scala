package im.mange.flyby

import im.mange.common.{OnShutdown, ProcessRunner}
import im.mange.driveby.DriveByConfig

object FlyServer {
  DriveByConfig.flyBinary match {
    case Some(fly) => {
      val flyProcess = new ProcessRunner("Fly", fly)
      flyProcess.start()
      OnShutdown.execute("Stop Fly", () => flyProcess.stop() )
    }
    case None => throw new RuntimeException("Unable to start fly, please set DriveByConfig.flyBinary")
  }

  ///all bets are off from here .....

  //make browser/start stopping also be BrowserCommands and end/start thread as required
  //TODO: flyBinaryLocation should be in DriveByConfig/FlyByConfig
  //TODO: do win32 ..OS.xxxx?

  //don't forget to check fly in ... (and probably add license)
  //private val script = if (OS.windows_?) "startFly.bat" else "startFly.sh"
  //private val fly = new ProcessRunner("Fly", "./" + script, "bin/fly/")
  //actually maybe be better to embed the binary in the jar anyway ...
  //... in which case startFly should be ported to scala

  //DriveByConfig.pool = remote(url)|local
  //or DriveByConfig.pool = localhost (optimise) | hostname:port

  // on suite startup .. start fly and remote pool - or local pool as required
  // - or perhaps always use a remote pool? (no doubt it's a bit slower)

  // or DriveByConfig.keepBrowsersOpen ....

  //TODO: have a jar that can be launched ...
  //e.g. driveby/flyby -server|-client ip etc
  //launch space and as appropriate
  //locate (and bundle) fly exec in in jar (see jruby classpath stuff for example)

  //TODO: server needs to find browser to target command for
  //browser will need to announce the type and it's id
  //class is RemoteBrowser
  //fly.write(Browser(browser.id), Long.MaxValue)

}