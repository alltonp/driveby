package im.mange.driveby.conditions

case class ElementFocused(by: By) extends Condition {

  def expectation = expect("ElementFocused", List(by.toString))
  def isSatisfied(browser: UnSafeBrowser) = { browser.hasFocus(by) == true }
  def describeFailure(browser: UnSafeBrowser) = { expectation + butWas(() => browser.hasFocus(by).toString ) }
}
