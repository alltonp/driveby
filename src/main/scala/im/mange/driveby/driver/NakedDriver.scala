package im.mange.driveby.driver

import im.mange.driveby.By

trait NakedDriver {
  def close()
  def findElements(by: By): Seq[NakedElement]
  def goto(url: String)
  def html: String
  def refresh()
  def screenshot: Array[Byte]
  def title: String
  def currentUrl: String
}
