package im.yagni.driveby.driver

trait NakedElement {
  def attribute(name: String): String
  def clear()
  def click()
  def enter(value: String)
  def isDisplayed: Boolean
  def isEnabled: Boolean
  def childrenCount: Int
  //TODO: sort out this shonky mess
  def option(value: String): Option[NakedElement]
//  def scrollTo()
  def text: String
  def yAxisLocation: Int
}
