package im.mange.flyby

import im.mange.driveby.BrowserCommand

//TODO: probably new BrowserId(id: Long, hostname: String)
case class RemoteCommand(browserCommand: BrowserCommand, browserId: Long,
                         var executed: java.lang.Boolean = null,
                         var succeeded: java.lang.Boolean = null,
                         var exceptionMessage: String = null)
