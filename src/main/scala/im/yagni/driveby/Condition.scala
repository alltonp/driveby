package im.yagni.driveby

import browser.UnSafeBrowser

trait Condition {
  def expectation: String
  def isSatisfied(browser: UnSafeBrowser): Boolean
  def describeFailure(browser: UnSafeBrowser): String
}
