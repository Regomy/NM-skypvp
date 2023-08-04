package me.rejomy.nmsp.me.rejomy.nmsp.util

import me.rejomy.nmsp.command.BroadCast
import me.rejomy.nmsp.command.Prefix
import me.rejomy.nmsp.command.Suffix
import org.bukkit.plugin.java.JavaPlugin

lateinit var INSTANCE: Main
class Main: JavaPlugin() {

    override fun onEnable() {
        INSTANCE = this
        getCommand("prefix").executor = Prefix()
        getCommand("suffix").executor = Suffix()
        getCommand("notify").executor = BroadCast()
    }

}