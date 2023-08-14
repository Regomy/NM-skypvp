package me.rejomy.nmsp.command

import me.rejomy.nmsp.util.EconomyManager
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import kotlin.math.floor

class BuyerNMSP : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if(sender !is ConsoleCommandSender) {
            sender.sendMessage("Команда может быть выполнена только с консоли!")
            return false
        }
        if(args.size < 4) {
            sender.sendMessage("Недостаточно аргументов ${args.size} < 4 НИК ЦЕНА ПРЕДМЕТ МАКС_КОЛ-ВО")
            return false
        }
        val player = Bukkit.getPlayer(args[0])?: return true
        var price = args[1].toInt()
        val material = args[2]
        var amount = floor(EconomyManager.getBalance(player) / price)
        if(amount > args[3].toInt()) {
            amount = (args[3].toInt()).toDouble()
        }
        if(amount < 1) {
            player.sendMessage("§cУ вас недостаточно средств!")
        } else {
            EconomyManager.takeMoney(player, amount * price)
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give ${args[0]} $material ${amount.toInt()}")
            player.sendMessage("§7» §fПокупка прошла успешно!")
        }
        return false
    }

}