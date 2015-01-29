package im.mange.common

import java.net.InetAddress

object Host {
  def name = InetAddress.getLocalHost.getHostName
}
