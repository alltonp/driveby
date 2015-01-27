package im.yagni.common

import collection.mutable.ListBuffer
import scala.Predef._

case class ShutdownFunction(message: String, function: () => Unit)

object OnShutdown {
  private val shutdownFunctions = new ListBuffer[ShutdownFunction]
  
  def execute(message: String, function: () => Unit) {
    shutdownFunctions.append(new ShutdownFunction(message, function))
  }
  
  Runtime.getRuntime addShutdownHook new Thread {
    override def run() {
      println("### Executing shutdowns: " + shutdownFunctions.map(_.message).mkString(", ") )

      shutdownFunctions.foreach(f => {
        try { f.function() }
        catch {
          case e: Exception => println("### Problem while executing shutdown " + f.message + ": " + e.getMessage)
        }
        finally { println("### Finished shutdown: " + f.message) }
      })

      println("### Shutdown complete")
    }
  }
}