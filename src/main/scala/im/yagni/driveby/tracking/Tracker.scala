package im.yagni.driveby.tracking

import org.apache.commons.io.FileUtils
import java.io.File
import im.yagni.driveby.DriveByConfig
import collection.mutable.ListBuffer
import report.{SpecificationRun, ExampleRun, BrowserRun, ApplicationRun}
import scala.Long
import scala.Predef._
import im.yagni.driveby.DriveByConfig._
import im.yagni.driveby.commands.Assert

object Format {
  import java.text.DecimalFormat

  private val millisFormat = new DecimalFormat("#####")
  private val countFormat = new DecimalFormat("##")

  def millis(duration: Option[Long]) = {
    val formatted = millisFormat.format(duration.getOrElse(0))
    " " * (5 - formatted.length) + formatted
  }

  def seconds(millis: Long) = (millis / 1000) + "s"

  def count(number: Long) = {
    val formatted = countFormat.format(number)
    " " * (2 - formatted.length) + formatted
  }
}

//TODO: in summary or main tracking - report on cowboy assertions
//TODO: better handle things not being available
object Tracker {
  import DriveByConfig._
  import Format._

  private var events = Vector[Event]()

  def add(event: Event) {
    if (!trackingEnabled) return
    //TODO: replace synchronized with threadsafe collection
    synchronized {
      if (trackingVerbose) println("### " + event)
      events = events :+ event
    }
  }

  //TODO: return an ExampleRun
  def allEvents(exampleId: Long) = events.filter(_.exampleId == exampleId)
  private def lastEvent = events.lastOption
  private def firstEvent = events.headOption

  private def totalElapsedMillis = for { l <- lastEvent; f <- firstEvent } yield l.at - f.at
  private def actualBrowserCount = events.filter(_.isInstanceOf[BrowserOpened]).size
  private def actualApplicationCount = events.filter(_.isInstanceOf[ApplicationStarted]).size
  private def requestedBrowserCount = events.filter(_.isInstanceOf[BrowserOpenRequested]).size
  private def requestedApplicationCount = events.filter(_.isInstanceOf[ApplicationStartRequested]).size
  private def specificationCount = events.filter(_.isInstanceOf[SpecificationStarted]).size
  private def exampleCount = examples.size
  //TODO: handle /0
  private def averageExampleMillis = totalExampleOnlyMillis / exampleCount
  private def assertionCount = assertions.size
  private def totalExampleOnlyMillis = examples.map(_.exampleOnlyMillis).sum
  private def examplesByDurationMillis = examples.sortBy(_.exampleOnlyMillis).reverse

  private def examples = {
    val idToEvents = events.filterNot(_.exampleId == -1).groupBy(_.exampleId)
    idToEvents.keys.map(id => {ExampleRun(id, idToEvents(id))}).toList
  }

  private def specifications = {
    val idToEvents = events.filterNot(_.specificationId == -1).groupBy(_.specificationId)
    idToEvents.keys.map(id => {SpecificationRun(id, idToEvents(id))}).toList
  }

  private def browsers = {
    val idToEvents = events.filterNot(_.browserId == -1).groupBy(_.browserId)
    idToEvents.keys.map(id => {BrowserRun(id, idToEvents(id))}).toList
  }

  private def applications = {
    val idToEvents = events.filterNot(_.applicationId == -1).groupBy(_.applicationId)
    idToEvents.keys.map(id => {ApplicationRun(id, idToEvents(id))}).toList
  }

  private def assertions = {
    val allCommands = events.filter(_.isInstanceOf[BrowserCommandExecuted])
    allCommands.map(_.asInstanceOf[BrowserCommandExecuted].command).filter(_.isInstanceOf[Assert])
  }

  def report() {
    if (!trackingEnabled) return
    synchronized {
      try {
        doSummaryReport()
        if (trackingFullDump) doDumpReport()
        println("### Finished DriveBy:" + List(summary, optimal).map("\n- " + _).mkString(""))
      } catch {
        case e: Exception => println("### Problem generating tracking report: " + e.getMessage); e.printStackTrace()
      }
    }
  }

  private def doDumpReport() {
    val content = events.map(e => e.at + "," +  e.toString).mkString("\n")
    FileUtils.writeStringToFile(new File(outputDir + "dump.csv"), content)
  }

  private def doSummaryReport() {
    val report = ListBuffer("DriveBy Report:")
    report.append("- " + summary)
    report.append("- " + optimal)

    //TODO: report on Browser open/close failures
    report.append("\nBrowsers:")
    browsers.sortBy(_.browserId).foreach(b => report.append(b.report))
    report.append("\nApplications:")
    applications.sortBy(_.applicationId).foreach(b => report.append(b.report))
    report.append("\nSpecifications:")
    specifications.sortBy(_.durationMillis).reverse.foreach(s => report.append(s.report))
    report.append("\nExamples:")
    examplesByDurationMillis.foreach(e => report.append(e.report))

    if (trackingVerbose) println(report.mkString("\n"))
    FileUtils.writeStringToFile(new File(outputDir + "tracking.txt"), report.mkString("\n"))
  }

  private def summary =
    "actual:  " + seconds(totalElapsedMillis.getOrElse(0)) + " for " +
      specificationCount + " Specifications, " +
      exampleCount + " Examples, " +
      assertionCount + " Assertions, using " +
      actualBrowserCount + " Browsers (of " + requestedBrowserCount + " requested), " +
      actualApplicationCount + " Applications (of " + requestedApplicationCount + " requested)"

  private def optimal =
    "optimal: " + (if(requestedBrowserCount > 0) seconds(totalExampleOnlyMillis / requestedBrowserCount) else "???") + " for current BuildConfig, " +
      "given " + seconds(totalExampleOnlyMillis) + " for all examples serially, " +
      "would require max " + averageExampleMillis + "ms per example"
}