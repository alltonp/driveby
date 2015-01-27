package im.yagni.driveby.tracking

import im.yagni.common.AtomicLongCounter

object TrackingIds {
  private val browserId = new AtomicLongCounter()
  private val specificationId = new AtomicLongCounter()
  private val exampleId = new AtomicLongCounter()

  def nextBrowserId = browserId.next
  def nextSpecificationId = specificationId.next
  def nextExampleId = exampleId.next
}