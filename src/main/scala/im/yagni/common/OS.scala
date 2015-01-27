package im.yagni.common

object OS {
  private val name = sys.props("os.name")

  def osx_? = name.matches("^Mac OS X$")
  def windows_? = name.matches("^Windows.*")
  def linux_? = name.matches("^Linux$")
}