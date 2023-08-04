package me.rejomy.nmsp.command

import me.rejomy.nmsp.INSTANCE
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import util.replace

class Suffix: CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {

        if(args.isEmpty()) {
            sender.sendMessage(replace(INSTANCE.config.getString("message.bad-argument").replace("%cmd", "prefix")))
            return true
        }

        args.drop(0)
        args.drop(0)

        if(Regex("&.").replace(args.toString(), "").length > 10) {
            sender.sendMessage(replace(INSTANCE.config.getString("message.bad-delay")))
            return true
        }

        var prefix = args.toString().replace("&k", "")
        if(!sender.hasPermission("nmsp.modified"))
            prefix = prefix.replace("&l", "").replace("&n", "").replace("&m", "")

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user ${sender.name} meta setsuffix $prefix")
        sender.sendMessage(replace(INSTANCE.config.getString("message.success").replace("%cmd", "prefix").replace("%prefix", prefix)))
        return false
    }


}