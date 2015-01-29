package im.mange.driveby.pool

trait ApplicationController {
  val application: Application

  def start()

  def stop()

  def hasStarted: Boolean
}
