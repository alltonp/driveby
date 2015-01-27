package im.yagni.flyby

//iteration 1:
//- have a seperate process which starts up 1 browser
//- makes it available via the pool
//- have a spec in driveby which runs using a browser in that pool
//- closes browser on shutdown

//how does this work:
//- client says take browser
//- do a take from fly space with template AvailableBrowser
//- marks it as in use and wraps it in a RemoteBrowser
//- client says goto - RemoteBrowser puts a Goto(browser:1) in space
//- server looks for commands for that browser - so will need a thread for each browser -  while true take()
//- server runs command put's result back in space
//- etc
//- client says write browser
//- (client or server) marks it available again

//other:
//- client/server is confusing here - agent perhaps?

//iteration 2:
//- support multiple browsers

//iteration 3:
//- support multiple browser types

//iteration x:
//- embed portable profile
//- make easy to distribute an agent
//- handle browsers dieing
//- restart after x commands for example
//- have pool on different machine to the tests are running
//- discover pools automatically

//aka the tuple - needs to have browser id in it
//later will need the machine id and browser type as well

//put this in
case class BrowserTake()
//get this back
case class BrowserWrite(/* id, Long */)

//should webdriver implement these directly instead? WebDriverCommandProcessor
//should the local browser create these commands as well

//def id: Long - should all command's have
//def screenshot(file: File) - should probably stream the bytes and write on the client instead

//instead store them here, just like the LocalBrowserPool
//store the browsers in a list
//to iterate on closedown

class RemoteBrowserPoolClient() {
  //TODO: write/take - need to stamp browsers as being (un/)available etc
}

//class RemoteBrowser /*extends Browser*/ {
//
//}

//TODO: ensure that all tracking is consistent between pool implementations
//  OnShutdown.execute("Close browsers", () =>
//    allBrowsers.map(browser => {
//      try {
//        browser.close()
//        Tracker.add(BrowserClosed(browser.id))
//      } catch {
//        case e => Tracker.add(BrowserCloseFailed(browser.id))
//      }
//    }))
