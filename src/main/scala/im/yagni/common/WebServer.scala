package im.yagni.common

import java.io.File
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.nio.SelectChannelConnector
import org.eclipse.jetty.webapp.WebAppContext
import org.eclipse.jetty.servlet.ServletContextHandler

class WebServer(port: Int, autoStart: Boolean = true, webAppPath: String = "src/main/webapp") {
  private val server = buildServer
  server.setHandler(createContext)

  def add(path: String) {
    val context = new ServletContextHandler
    context.setServer(server)
    context.setContextPath(path)
  }

  def start() {
    try {
      server.start()
      println("### Started web server on port %d...".format(port))
      while (!server.isStarted) Thread.sleep(100)
    } catch {
      case e: Exception => {
        println("### Failed to start web server on port %d".format(port))
        e.printStackTrace()
        throw e
      }
    }
  }

  def stop() {
    server.stop()
    val end = System.currentTimeMillis() + 10000
    while (!server.isStopped && end > System.currentTimeMillis()) Thread.sleep(100)
    if (!server.isStopped) println("!!!!!!! SERVER FAILED TO STOP !!!!!!!")
  }

  private def buildServer = {
    val server = new Server
    val scc = new SelectChannelConnector
    scc.setPort(port)
    scc.setAcceptors(Runtime.getRuntime.availableProcessors() * 2)
    scc.setResponseBufferSize(1000000)
    server.setConnectors(Array(scc))
    server.setStopAtShutdown(true)
    server
  }

  private def createContext = {
    val context = new WebAppContext
    context.setServer(server)
    context.setContextPath("/")
    //TIP: jetty won't start if webapp directory doesnt exist - should probably try to create it
    if (new File(webAppPath).exists()) context.setWar(webAppPath) else context.setWar(getClass.getClassLoader().getResource("webapp").toExternalForm())
    context
  }

  OnShutdown.execute("Stop web server", stop _)

  if (autoStart) start()
}