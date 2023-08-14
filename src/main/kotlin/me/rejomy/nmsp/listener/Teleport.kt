package me.rejomy.nmsp.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.potion.PotionEffectType

class Teleport : Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onTeleport(event: PlayerTeleportEvent) {

        val player = event.player

        if(player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE))
            player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE)
        if(player.hasPotionEffect(PotionEffectType.SPEED))
            player.removePotionEffect(PotionEffectType.SPEED)

    }

}