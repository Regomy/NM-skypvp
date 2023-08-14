package me.rejomy.nmsp

import me.rejomy.nmsp.util.EconomyManager
import me.rejomy.nmsp.command.*
import me.rejomy.nmsp.listener.*
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

lateinit var INSTANCE: Main

class Main : JavaPlugin() {

    override fun onEnable() {
        INSTANCE = this
        saveDefaultConfig()

        // Init economy
        EconomyManager.init()

        // Command register
        if (config.getBoolean("command.bc"))
            getCommand("notify").executor = BroadCast()
        if (config.getBoolean("command.head"))
            getCommand("head").executor = Head()
        if (config.getBoolean("command.prefix"))
            getCommand("prefix").executor = Prefix()
        if (config.getBoolean("command.suffix"))
            getCommand("suffix").executor = Suffix()
        getCommand("buyernmsp").executor = BuyerNMSP()

        // Handler register
        Bukkit.getPluginManager().registerEvents(Potion(), this)
        Bukkit.getPluginManager().registerEvents(Damage(), this)
        Bukkit.getPluginManager().registerEvents(Anvil(), this)
        Bukkit.getPluginManager().registerEvents(Teleport(), this)
    }

}