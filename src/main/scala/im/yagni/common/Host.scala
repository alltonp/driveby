package im.yagni.common

import java.net.InetAddress

object Host {
  def name = InetAddress.getLocalHost.getHostName
}
