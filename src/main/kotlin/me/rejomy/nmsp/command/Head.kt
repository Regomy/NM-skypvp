package me.rejomy.nmsp.command

import me.rejomy.nmsp.INSTANCE
import me.rejomy.nmsp.util.replace
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class Head : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {

        if(args.isEmpty()) {
            sender.sendMessage(replace(INSTANCE.config.getString("message.bad-argument")).replace("%cmd", "head"))
            return true
        }

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give ${sender.name} skull 1 3 {SkullOwner:\"${args[0]}\"}")
        sender.sendMessage(replace(INSTANCE.config.getString("message.success-head")).replace("%cmd", "head"))
        return false
    }
}