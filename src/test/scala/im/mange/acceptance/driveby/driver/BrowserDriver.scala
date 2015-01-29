package im.mange.acceptance.driveby.driver

import im.mange.driveby.browser.Browser
import im.mange.driveby.Example
import im.mange.WebSpecificationSuite
import scala.xml.NodeSeq

class BrowserDriver(baseUrl: String, browser: Browser, example: Example) {
  def page(html: NodeSeq) = {
    val path = "/" + example.id
    addPage(path, html)
    browser.goto(baseUrl + path)
    browser
  }
  
  def addPage(path: String, html: NodeSeq) = {
    WebSpecificationSuite.server.add(path, staticContent(html))
    this
  }

  private def staticContent(html: NodeSeq) = {
    import javax.servlet.http.{ HttpServletResponse, HttpServletRequest, HttpServlet }

    new HttpServlet {
      override def doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.getOutputStream.print(html.toString())
      }
    }
  }
}