package im.mange.driveby.browser

trait InternalBrowser extends Browser {
  var exampleId: Long = -1
  def close()
  def id: Long
  var mileage: Long
}
