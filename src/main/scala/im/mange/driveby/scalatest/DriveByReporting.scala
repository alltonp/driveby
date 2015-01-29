package im.mange.driveby.scalatest

import im.mange.driveby._
import im.mange.driveby.DriveByConfig._
import im.mange.driveby.report.Reporter
import im.mange.driveby.Example

trait DriveByReporting extends ExampleAware with BrowserAware {
  self: SpecificationAware =>

  override def onFailure = reportFailure _ :: super.onFailure
  override def afterExample = if (reportAlways) reportAll _ :: super.afterExample else super.afterExample

  private def reportFailure(example: Example, message: String) {
    Reporter.report(message, browser, example, specification, failed = true)
  }

  private def reportAll(example: Example) {
    Reporter.report("", browser, example, specification, failed = false)
  }

}