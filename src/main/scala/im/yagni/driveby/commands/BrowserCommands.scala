package im.yagni.driveby.commands

import im.yagni.driveby.{Condition, By, BrowserCommand}

//TIP: all fields have to be null by default, since they are seriaised across the space
//TODO: auto generate id on executing/adding to the pool
case class Assert(/* id, Long */ condition: Condition, message: String = null, var result: java.lang.Boolean = null) extends BrowserCommand
case class Clear(/* id, Long */ by: By) extends BrowserCommand
case class Click(/* id, Long */ by: By) extends BrowserCommand
case class Close(/* id, Long */) extends BrowserCommand
case class Enter(/* id, Long */ by: By, value: String, clear: Boolean) extends BrowserCommand
case class Goto(/* id, Long */ url: String) extends BrowserCommand
case class Html(/* id, Long */ var result: String = "") extends BrowserCommand
case class Refresh(/* id, Long */) extends BrowserCommand
case class Screenshot(/* id, Long */ var result: Array[Byte] = null) extends BrowserCommand
case class Select(/* id, Long */ by: By, value: String) extends BrowserCommand
//case class ScrollTo(/* id, Long */ by: By) extends BrowserCommand
