package im.mange.driveby

trait CommandExecutor {
  def execute(command: BrowserCommand): BrowserCommand
}
