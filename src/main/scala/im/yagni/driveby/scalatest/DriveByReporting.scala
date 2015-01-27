package im.yagni.driveby.scalatest

import im.yagni.driveby._
import im.yagni.driveby.DriveByConfig._
import im.yagni.driveby.report.Reporter
import im.yagni.driveby.Example

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