package im.mange.common

object Times {
  def times(n: Int) (code: => Unit) {
    for (i <- 1 to n) code
  }
}
