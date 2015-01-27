package im.yagni.acceptance.driveby.scalatest.browser

import im.yagni.acceptance.driveby.scalatest.WebSpecification
import org.scalatest.Matchers

class HtmlSpec extends WebSpecification with Matchers {
  def `be the page html` {
    val html = <body>html</body>
    given.page(html).html should include(html.toString())
  }
}