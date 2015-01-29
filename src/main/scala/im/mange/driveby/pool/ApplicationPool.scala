package im.mange.driveby.pool

import im.mange.common.Wait._
import im.mange.driveby.tracking._
import im.mange.driveby.{DriveByConfig, Example}

import scala.actors.threadpool.{LinkedBlockingQueue, TimeUnit}
import scala.collection.mutable.ListBuffer
import scala.concurrent.Future

//TODO: remove disgrace
//TODO: support tracking properly, not Info
object ApplicationPool {
  //TODO: should make browsers work the same, so can mix and match
  private val applicationControllers = DriveByConfig.applicationControllers
  import scala.concurrent.ExecutionContext.Implicits.global

  private val allApplications = new ListBuffer[Application]
  private val availableApplications = new LinkedBlockingQueue[Application]
  private var terminallyIll = false

  def write(application: Option[Application], example: Example) {
    application match {
      case Some(a) => {
        Tracker.add(ApplicationWritten(example.id, a.port))
        availableApplications.put(a)
      }
      case None => Tracker.add(Info("ApplicationWriteRequestedForDead", example.id))
    }
  }

  def take(example: Example): Option[Application] = {
    if (terminallyIll) {
      //      Tracker.add
      return None
    }

    Tracker.add(ApplicationTakeRequested(example.id))
    val application = availableApplications.poll(DriveByConfig.applicationTakeWaitTimeout, TimeUnit.MILLISECONDS)
    if (application == null) {
      Tracker.add(ApplicationTakeTimeout(example.id))
      None
    } else {
      Tracker.add(ApplicationTaken(example.id, application.port))
      Some(application)
    }
  }

  def fill() {
    println("### Warming up applications ... " + applicationControllers)

    try {
      applicationControllers.map(a =>
        Future {
          try {
            println("### Starting " + a.application)
            Tracker.add(ApplicationStartRequested(a.application.name, a.application.port))
            a.start()
            waitUpTo(DriveByConfig.applicationStartUpWaitTimeout).forCondition(a.hasStarted, a.application + " did not start")
            ApplicationPool.add(a.application)
            Tracker.add(ApplicationStarted(a.application.name, a.application.port))
          }
          catch {
            case e: Exception => {
              println("### Problem starting " + a.application + ", " + e.getMessage)
              Tracker.add(ApplicationStartFailed(a.application.name, a.application.port))
            }
          }
        }
      )
    }
    finally {
      //TODO: support same style in BrowserPool
      waitUpTo(DriveByConfig.applicationStartUpWaitTimeout).forCondition(allApplications.size > 0,
        "### Epic Fail, couldn't start up any application instances", () => { terminallyIll = true} )
    }
  }

  def empty() {
     //TODO: track how long fill and empty take
    applicationControllers.par.map(a =>
      try {
        Tracker.add(ApplicationStopRequested(a.application.port))
        a.stop()
        Tracker.add(ApplicationStopped(a.application.port))
      } catch {
        case e: Exception => {
          Tracker.add(ApplicationStopFailed(a.application.port))
          println("### " + a + " failed to stop, due to " + e.getMessage)
        }
      }
    )
  }

  private def add(application: Application) {
    allApplications.append(application)
    availableApplications.put(application)
  }
}