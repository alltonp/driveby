package im.yagni.common

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.nio.SelectChannelConnector
import org.eclipse.jetty.servlet.{ServletHolder, ServletContextHandler}
import javax.servlet.http.HttpServlet

class WebServerForServlets(port: Int, autoStart: Boolean = true) {
  private val server = buildServer
  private val context = createContext
  server.setHandler(context)

  def add(path: String, servlet: HttpServlet) {
    context.addServlet(new ServletHolder(servlet), path)
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
    val context = new ServletContextHandler
    context.setServer(server)
    context.setContextPath("/")
    context
  }

  OnShutdown.execute("Stop web server", () => stop())

  if (autoStart) start()
}