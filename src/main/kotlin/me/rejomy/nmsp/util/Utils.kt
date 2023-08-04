package util

import org.bukkit.ChatColor

fun replace(message: String): String {

    return ChatColor.translateAlternateColorCodes('&', message)

}