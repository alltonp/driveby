package im.mange.flyby

import im.mange.driveby.Example

object HelloFlyBy extends App {
  println("Doing")

  val example = Example("foo", 1)

  val pool = new RemoteBrowserPool()
  val browser = pool.take(example)
  browser.get.goto("http://news.bbc.co.uk")
  browser.get.goto("http://www.google.com")
  browser.get.goto("http://www.yahoo.com")
  pool.write(browser, example)

  println("Done")
}
