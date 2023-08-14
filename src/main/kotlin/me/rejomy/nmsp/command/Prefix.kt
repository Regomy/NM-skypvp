package me.rejomy.nmsp.command

import me.rejomy.nmsp.util.argsChecker
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class Prefix: CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        argsChecker(args, sender, "prefix")
        return false
    }


}