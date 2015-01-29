package im.mange.driveby

object Describer {
  def butWas(f: () => String) = {
    try {
      " but was \"" + f() + "\""
    } catch {
      case e: Exception  => " but was ***unknown*** - due to a failure describing the actual: " + e.getMessage
    }
  }

  def expect(command: String, args: List[String]) = command + "(" + args.map("\"" + _ + "\"").mkString(", ") + ")"
}