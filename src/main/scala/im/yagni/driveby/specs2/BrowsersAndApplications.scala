package im.yagni.driveby.specs2

import im.yagni.driveby.ApplicationAware

trait BrowsersAndApplications extends Browsers with Applications {
  //TODO: ApplicationAware isnt in right place really, but its needed it seems
  trait BrowsersAndApplications extends Browsers with Applications with ApplicationAware
}