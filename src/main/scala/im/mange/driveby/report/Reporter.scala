package im.mange.driveby.report

import im.mange.driveby.browser.Browser
import im.mange.driveby.{Specification, Example}
import org.joda.time.DateTime
import org.apache.commons.io.FileUtils
import java.io.File
import im.mange.driveby.tracking.{Event, BrowserCommandExecuted, Tracker}
import collection.mutable.ListBuffer
import im.mange.driveby.commands.{Screenshot, Html}
import im.mange.driveby.DriveByConfig._
import org.joda.time.format.DateTimeFormat

object Reporter {
  val fmt = DateTimeFormat.forPattern("kk:mm:ss:SS")

  def tidyUp() { FileUtils.deleteDirectory(new File(outputDir)) }

  def report(message: String, browser: Browser, example: Example, specification: Specification, failed: Boolean = true) {
    val actualPath = outputDir + (if (failed) "failed/" else "all/")
    val uniqueName = example.id + "_" + specification.name

    val screenshotFilename = new File(actualPath + "capture/" + uniqueName + ".png")
    browser.screenshot(screenshotFilename)

    val htmlFilename = new File(actualPath + "capture/" + uniqueName + ".html")
    FileUtils.writeStringToFile(htmlFilename, browser.html)

    val content = report(message, screenshotFilename, example, htmlFilename)
    FileUtils.writeStringToFile(new File(actualPath + uniqueName + ".html"), content.toString())
  }

  private def report(message: String, screenshot: File, example: Example, html: File) =
    //TODO: include spec name and example desc in report
    <body>
      <h3>{example.description}</h3>
      <p><pre>{fmt.print(new DateTime)}: {message}</pre></p><hr/>
      <img src={"capture/" + screenshot.getName}/><hr/>
      <pre>{scala.xml.Unparsed(renderEvents(example.id))}</pre><hr/>
    </body>

  //      <iframe width="100%" height="100%" frameborder="0" src={s"capture/${html.getName}"}></iframe>

  private def renderEvents(exampleId: Long): String = {
    val interesting = Tracker.allEvents(exampleId).foldLeft(new ListBuffer[String]()) {
      (accumulator, event) => {
        event match {
          case BrowserCommandExecuted(Html(_), _) =>
          case BrowserCommandExecuted(Screenshot(_), _) =>
          case e@BrowserCommandExecuted(c, _) => append(accumulator, e, c.toString)
          case e => append(accumulator, e, e.toString)
        }
        accumulator
      }
    }
    interesting.map(e => "<li><b>" + e + "</b></li>").mkString
  }

  private def append(accumulator: ListBuffer[String], e: Event, m: String) {
    accumulator.append(fmt.print(e.at) + " - " + m)
  }
}