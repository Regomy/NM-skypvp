package me.rejomy.nmsp.listener

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PotionSplashEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.Potion
import org.bukkit.potion.PotionEffectType


class Potion : Listener {

    @EventHandler
    fun onPotionSplash(event: PotionSplashEvent) {

        if(event.potion.effects.stream().anyMatch { it.type == PotionEffectType.INCREASE_DAMAGE || it.type == PotionEffectType.SPEED }) {
            event.isCancelled = true
        }

    }

    @EventHandler
    fun onPotionDrink(event: PlayerItemConsumeEvent) {
        val item: ItemStack = event.item
        if (item.type != Material.POTION) return
        for (effect in Potion.fromItemStack(item).effects) {
            if (effect.type == PotionEffectType.INCREASE_DAMAGE || effect.type == PotionEffectType.SPEED) {
                event.isCancelled = true
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + event.player.name + " 1")
                event.player.sendMessage("Вы не можете использовать это зелье!")
                break
            }
        }
    }

}