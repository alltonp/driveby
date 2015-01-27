package im.yagni.acceptance.driveby.scalatest

//issues:
//- no single test running in intellij
//- no navigation to test from failure in intellij
//- shutdown of browsers not occuring
//- not configured for in ide
//- seem to have to start with 'test'
class DumbSpikeSpec extends WebSpecification {
  def testPass() {
    println(browser)
    println(given)
    assertResult(2) { 1 + 1 }
  }

  def testPass2() {
    println(browser)
    println(given)
    assertResult(2) { 1 + 1 }
  }

  //TEMP: ensuring specs2 and scalatest interrop ok
//  def testFail() {
//    println(browser)
//    expectResult(3) { 1 + 1 }
//  }

//  def testFalse() {
////    println(browser)
//    //TODO: this will cause problems in migration ... fails in specs2, but not in scalatest
//    false
//  }
//
//  def testException() {
////    println(browser)
//    throw new Exception("exception")
//  }
}