package im.mange.driveby.report

import im.mange.driveby.{ExampleAware, BrowserAware, SpecificationAware, Example}
import im.mange.driveby.DriveByConfig._

trait Reporting extends ExampleAware {
  self: SpecificationAware with BrowserAware =>

  override def onFailure = reportFailure _ :: super.onFailure
  override def afterExample = if (reportAlways) reportAll _ :: super.afterExample else super.afterExample

  private def reportFailure(example: Example, message: String) {
    Reporter.report(message, browser, example, specification, failed = true)
  }

  private def reportAll(example: Example) {
    Reporter.report("", browser, example, specification, failed = false)
  }
}