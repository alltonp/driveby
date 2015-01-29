package im.mange.common

class AtomicStringCounter(name: String = "", start: Long = 1) {
  private var count = start - 1

  def next = synchronized {
    count += 1
    name + count
  }
}

class AtomicLongCounter(start: Long = 1) {
  private var count = start - 1

  def next = synchronized {
    count += 1
    count
  }
}

class AtomicIntCounter(start: Int = 1) {
    private var count = start - 1

    def next = synchronized {
      count += 1
      count
    }
}