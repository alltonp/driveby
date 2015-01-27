package im.yagni.driveby.pool

trait ApplicationController {
  val application: Application

  def start()

  def stop()

  def hasStarted: Boolean
}
