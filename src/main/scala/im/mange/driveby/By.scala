package im.mange.driveby

trait By

case class Id(id: String) extends By
case class Class(className: String) extends By