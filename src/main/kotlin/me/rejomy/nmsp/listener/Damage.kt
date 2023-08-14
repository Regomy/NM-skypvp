package me.rejomy.nmsp.listener

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent

class Damage : Listener {

    @EventHandler(priority = EventPriority.LOW)
    fun onDamage(event: EntityDamageEvent) {
        val entity = event.entity
        if(entity !is Player || event.cause != EntityDamageEvent.DamageCause.VOID || entity.world.name != "duel") return
        entity.damage(9999.9)
    }

}