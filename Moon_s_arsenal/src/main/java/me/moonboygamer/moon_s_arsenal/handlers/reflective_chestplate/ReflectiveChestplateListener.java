package me.moonboygamer.moon_s_arsenal.handlers.reflective_chestplate;

import me.moonboygamer.moon_s_arsenal.util.CooldownManager;
import me.moonboygamer.moon_s_arsenal.util.CustomKeys;
import me.moonboygamer.moon_s_arsenal.weapons.ReflectiveChestplate;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ReflectiveChestplateListener implements Listener {
    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player && event.getDamager() instanceof LivingEntity) {
            //check if they're wearing the chestplate
            //if they are, check if it has 4 charges
            //if it does, double the damage back to the attacker and reset charges
            //if it doesn't, add a charge
            Player player = (Player) event.getEntity();
            LivingEntity attacker = (LivingEntity) event.getDamager();
            if(player.getInventory().getItem(EquipmentSlot.CHEST) != null) {
                if(player.getInventory().getItem(EquipmentSlot.CHEST).getItemMeta().getPersistentDataContainer().has(CustomKeys.ReflectiveChestplate.getKey(), CustomKeys.ReflectiveChestplate.getType()) && (boolean) player.getInventory().getItem(EquipmentSlot.CHEST).getItemMeta().getPersistentDataContainer().get(CustomKeys.ReflectiveChestplate.getKey(), CustomKeys.ReflectiveChestplate.getType()) == true) {
                    if(player.getInventory().getItem(EquipmentSlot.CHEST).getItemMeta().getPersistentDataContainer().has(CustomKeys.ReflectiveChestplateCharges.getKey(), CustomKeys.ReflectiveChestplateCharges.getType())) {
                        if(CooldownManager.getManager(player).hasCooldown(CustomKeys.ReflectiveChestplateCooldown.getKey())) {
                            return;
                        }
                        int charges = (int) player.getInventory().getItem(EquipmentSlot.CHEST).getItemMeta().getPersistentDataContainer().get(CustomKeys.ReflectiveChestplateCharges.getKey(), CustomKeys.ReflectiveChestplateCharges.getType());
                        if(charges == 4) {
                            attacker.damage(event.getDamage() * 2);
                            ItemMeta meta = player.getInventory().getItem(EquipmentSlot.CHEST).getItemMeta();
                            meta.getPersistentDataContainer().set(CustomKeys.ReflectiveChestplateCharges.getKey(), CustomKeys.ReflectiveChestplateCharges.getType(), 0);
                            List<String> lore = meta.getLore();
                            lore.set(1, ChatColor.GRAY + "Charges: " + "0" + "/4");
                            meta.setLore(lore);
                            player.getInventory().getItem(EquipmentSlot.CHEST).setItemMeta(meta);
                            CooldownManager.getManager(player).addCooldown(CustomKeys.ReflectiveChestplateCooldown.getKey(), 20*15);
                            player.sendMessage("Reflective Chestplate is cooling down!");
                        } else {
                            ItemMeta meta = player.getInventory().getItem(EquipmentSlot.CHEST).getItemMeta();
                            List<String> lore = meta.getLore();
                            meta.getPersistentDataContainer().set(CustomKeys.ReflectiveChestplateCharges.getKey(), CustomKeys.ReflectiveChestplateCharges.getType(), charges + 1);
                            lore.set(1, ChatColor.GRAY + "Charges: " + (charges + 1) + "/4");
                            meta.setLore(lore);
                            player.getInventory().getItem(EquipmentSlot.CHEST).setItemMeta(meta);
                        }
                    }
                }
            }
        }
    }
}
