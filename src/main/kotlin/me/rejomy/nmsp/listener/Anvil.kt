package me.rejomy.nmsp.listener

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.meta.EnchantmentStorageMeta

val blackEnchantment = HashMap<Enchantment, Int>()

class Anvil : Listener {

    init {
        blackEnchantment[Enchantment.KNOCKBACK] = 2
        blackEnchantment[Enchantment.DAMAGE_ALL] = 5
        blackEnchantment[Enchantment.DAMAGE_ARTHROPODS] = 5
        blackEnchantment[Enchantment.DAMAGE_UNDEAD] = 5
        blackEnchantment[Enchantment.ARROW_DAMAGE] = 8
        blackEnchantment[Enchantment.ARROW_KNOCKBACK] = 2
        blackEnchantment[Enchantment.DURABILITY] = 2
        blackEnchantment[Enchantment.THORNS] = 10
        blackEnchantment[Enchantment.PROTECTION_PROJECTILE] = 7
        blackEnchantment[Enchantment.PROTECTION_ENVIRONMENTAL] = 4
        blackEnchantment[Enchantment.LOOT_BONUS_MOBS] = 2
        blackEnchantment[Enchantment.LOOT_BONUS_BLOCKS] = 2
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.type != InventoryType.ANVIL) return

        var item = event.currentItem
        var first = event.inventory.getItem(0)
        var second = event.inventory.getItem(1)

        if(first == null && second != null)
            first = item
        if(second != null && first == null)
            second = item

        val sum = first?.enchantments?.toMutableMap() ?: HashMap<Enchantment, Int>()

        if (second != null && first != null
            && second.type != Material.ENCHANTED_BOOK && first.type != Material.ENCHANTED_BOOK) {

            for (enc2 in second.enchantments) {
                if(enc2.key == Enchantment.THORNS) continue
                if (sum.containsKey(enc2.key)) {
                    val encLvl = sum[enc2.key]!!
                    var boost = if (encLvl > enc2.value) encLvl else (enc2.value + if (encLvl == enc2.value) 1 else 0)

                    for (black in blackEnchantment)
                        if (black.key == enc2.key && boost > black.value)
                            if (enc2.value < black.value && sum.containsKey(black.key) && sum[black.key]!! < black.value)
                                boost = black.value
                            else
                                boost -= 1
                    sum[enc2.key] = boost
                } else {
                    sum[enc2.key] = enc2.value
                }
            }

        }

        val result = event.inventory.getItem(2)

        if (result != null) {
            if (second != null && first != null && second.type != Material.ENCHANTED_BOOK && first.type != Material.ENCHANTED_BOOK) {
                for (enc in result.enchantments) {
                    result.removeEnchantment(enc.key)
                }
                for (enc in sum) {
                    result.addUnsafeEnchantment(enc.key, enc.value)
                }
            }
            val itemMeta = result.itemMeta
            if (itemMeta != null && itemMeta.hasDisplayName()) {
                val displayName = itemMeta.displayName

                // Заменяем символы "&" на символы "§"
                val coloredDisplayName: String = ChatColor.translateAlternateColorCodes('&', displayName)

                // Устанавливаем измененное имя предмету
                itemMeta.displayName = coloredDisplayName
                result.itemMeta = itemMeta
            }

        } else if (first != null && second != null) {

            if (event.slot != 2) return

            if(second.type == Material.ENCHANTED_BOOK) {
                val meta: EnchantmentStorageMeta = second.itemMeta as EnchantmentStorageMeta
                for (enc2 in meta.storedEnchants) {
                    if (sum.containsKey(enc2.key)) {
                        val encLvl = sum[enc2.key]!!
                        var boost =
                            if (encLvl > enc2.value) encLvl else (enc2.value + if (encLvl == enc2.value) 1 else 0)
                        for (black in blackEnchantment)
                            if (black.key == enc2.key)
                                if (boost > black.value)
                                    boost = black.value
                        sum[enc2.key] = boost
                    } else {
                        sum[enc2.key] = enc2.value
                    }
                }
            }

            val scopy = first.clone()

            scopy.addUnsafeEnchantments(sum)

            event.whoClicked.inventory.addItem(scopy)
            event.inventory.setItem(0, null)
            event.inventory.setItem(1, null)
        }

    }

}