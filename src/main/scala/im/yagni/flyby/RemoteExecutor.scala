package im.yagni.flyby

import im.yagni.driveby.{BrowserCommand, CommandExecutor}
import im.yagni.common.ConditionNotMetException

class RemoteExecutor(space: FlySpace, browserId: Long) extends CommandExecutor {
  def execute(command: BrowserCommand): BrowserCommand = {
    val toWrite = RemoteCommand(command, browserId, executed = false, succeeded = false)
    space.write(toWrite)

    val toTakeTemplate = RemoteCommand(null, browserId, executed = true, succeeded = null)
    val result = space.take(toTakeTemplate)

    //TODO: need to handle the option properly here
    //TODO: need to use the config timeout ...

    val commandResult = result.get
    if (!commandResult.succeeded) { throw new ConditionNotMetException(commandResult.exceptionMessage) }
    commandResult.browserCommand
  }
}