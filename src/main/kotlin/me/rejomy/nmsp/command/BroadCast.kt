package me.rejomy.nmsp.command

import me.rejomy.nmsp.INSTANCE
import me.rejomy.nmsp.util.arraytostring
import me.rejomy.nmsp.util.replace
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class BroadCast : CommandExecutor {

    val delay = HashMap<String, Long>()
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {

        if (args.isEmpty()) {
            sender.sendMessage(replace(INSTANCE.config.getString("message.bad-argument")).replace("%cmd", "notify"))
            return true
        }
        if (delay.containsKey(sender.name) && (System.currentTimeMillis() - delay[sender.name]!! < 180000)) {
            sender.sendMessage(replace(INSTANCE.config.getString("message.bad-delay")))
            return true
        }

        delay[sender.name] = System.currentTimeMillis()

        val message = arraytostring(args)
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bc $message &7(Пишет ${sender.name})")

        return false
    }
}