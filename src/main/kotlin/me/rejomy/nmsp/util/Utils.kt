package me.rejomy.nmsp.util

import me.rejomy.nmsp.INSTANCE
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

fun replace(message: String): String {

    return ChatColor.translateAlternateColorCodes('&', message)

}

fun arraytostring(args: Array<out String>): String {
    var msg = ""
    for (i in args)
        msg = if(msg.isNotEmpty()) "$msg $i" else "$i"
    return msg
}

fun argsChecker(args: Array<out String>, sender: CommandSender, type: String): Boolean {

    if(args.isEmpty()) {
        sender.sendMessage(replace("Ваш $type был сброшен"))
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user ${sender.name} meta remove$type 33")
        return true
    }

    val line = arraytostring(args)

    if(Regex("&.").replace(line, "").length > 10) {
        sender.sendMessage(replace(INSTANCE.config.getString("message.bad-length").replace("%cmd", type)))
        return true
    }

    var title = line.replace("&k", "")

    if(!sender.hasPermission("nmsp.modified"))
        title = title.replace("&l", "").replace("&n", "").replace("&m", "")

    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user ${sender.name} meta set$type 33 \"$title\"")

    sender.sendMessage(
        replace(
            INSTANCE.config.getString("message.success").replace("%cmd", type).replace("%prefix", title)
        )
    )

    return false
}