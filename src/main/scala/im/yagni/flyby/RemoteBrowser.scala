package im.yagni.flyby

import org.joda.time.DateTime


//TODO: probably new BrowserId(id: Long, hostname: String)
case class RemoteBrowser(browserType: String = null, browserId: java.lang.Long = null, hostname: String = null,
                         var free: java.lang.Boolean = null, mileage: java.lang.Long = null, lastUsed: DateTime = null)
