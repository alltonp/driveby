package im.mange.driveby.pool

import im.mange.little.LittleServer

case class InProcessApplicationController(name: String, port: Int, hostname: String = java.net.InetAddress.getLocalHost.getHostName) extends ApplicationController {
  val application = Application(name, port, hostname)

  beforeStart()
  val server = new LittleServer(port/* false*/)

  def beforeStart() {}
  def start() { /*server.start()*/ }
  def hasStarted = true
  def stop() { }
}