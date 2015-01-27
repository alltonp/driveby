package im.yagni.driveby

trait CommandExecutor {
  def execute(command: BrowserCommand): BrowserCommand
}
