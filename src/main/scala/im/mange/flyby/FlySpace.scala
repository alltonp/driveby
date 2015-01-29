package im.mange.flyby

import com.zink.scala.fly.ScalaFly
import im.mange.driveby.DriveByConfig

class FlySpace(hostname: String) {
  private var fly = ScalaFly.makeFly(host = hostname) match {
    case Left(t) => throw new RuntimeException("Unable to start fly", t)
    case Right(f) => f
  }

  def read(entry: AnyRef, waitTime: Long = DriveByConfig.flySpaceDefaultTimeoutMillis) = fly.read(entry, waitTime)
  def write(entry: AnyRef, leaseTime: Long = DriveByConfig.flySpaceDefaultTimeoutMillis) = fly.write(entry, leaseTime)
  def take[T <: AnyRef](template: T, waitTime: Long = DriveByConfig.flySpaceDefaultTimeoutMillis) = fly.take(template, waitTime)
  def readMany(entry: AnyRef, matchLimit: Long = DriveByConfig.flySpaceDefaultMatchLimit) = fly.readMany(entry, matchLimit)
  def writeMany(entries: Iterable[AnyRef], leaseTime: Long = DriveByConfig.flySpaceDefaultTimeoutMillis) = fly.writeMany(entries, leaseTime)
  def takeMany[T <: AnyRef](template: T, matchLimit: Long = DriveByConfig.flySpaceDefaultMatchLimit) = fly.takeMany(template, matchLimit)
}
