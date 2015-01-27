package im.yagni.driveby.pool

//TODO: seems like application stuff should be in application package (like browser)
case class Application(name: String, port: Int, host: String = "127.0.0.1") {
  def baseUrl = "http://" + host + ":" + port
}
