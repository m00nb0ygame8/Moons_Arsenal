package me.moonboygamer.moon_s_arsenal.handlers.transfersword;

import me.moonboygamer.moon_s_arsenal.Moon_s_arsenal;
import me.moonboygamer.moon_s_arsenal.util.CooldownManager;
import me.moonboygamer.moon_s_arsenal.util.CustomKeys;
import me.moonboygamer.moon_s_arsenal.weapons.TransferSword;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TransferSwordListener implements Listener {
    @EventHandler
    public void onPlayerRC(PlayerInteractEvent event) {
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(event.getPlayer().getInventory().getItem(EquipmentSlot.HAND) != null) {
                if(event.getPlayer().getInventory().getItem(EquipmentSlot.HAND).getItemMeta().getDisplayName().equals(TransferSword.getTransferSword().getItemMeta().getDisplayName())) {
                    if(CooldownManager.getManager(event.getPlayer()).hasCooldown(CustomKeys.TransferSwordCooldown.getKey())) {
                        event.getPlayer().sendMessage("Transfer Sword is on cooldown");
                        return;
                    }
                    event.getPlayer().getActivePotionEffects().forEach(potionEffect -> {
                        int hate = (int) event.getPlayer().getInventory().getItem(EquipmentSlot.HAND).getItemMeta().getPersistentDataContainer().get(CustomKeys.TransferSwordHate.getKey(), CustomKeys.TransferSwordHate.getType());
                        if(hate < 10) {
                            hate++;
                            event.getPlayer().getInventory().getItem(EquipmentSlot.HAND).getItemMeta();
                            ItemMeta meta = event.getPlayer().getInventory().getItem(EquipmentSlot.HAND).getItemMeta();
                            List<String> lore = new ArrayList<>();
                            lore.add(ChatColor.GRAY + "Stores potion effects as Hate");
                            lore.add(ChatColor.GRAY + "Hate: " + hate + "/10");
                            meta.getPersistentDataContainer().set(CustomKeys.TransferSwordHate.getKey(), CustomKeys.TransferSwordHate.getType(), hate);
                            meta.setLore(lore);
                            event.getPlayer().getInventory().getItem(EquipmentSlot.HAND).setItemMeta(meta);
                            //remove potion effect
                            event.getPlayer().removePotionEffect(potionEffect.getType());
                        }
                    });
                }
            }
        }
    }
    @EventHandler
    public void onHateHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
            Player player = (Player) event.getDamager();
            LivingEntity target = (LivingEntity) event.getEntity();
            if(player.getInventory().getItem(EquipmentSlot.HAND).getItemMeta().getDisplayName().equals(TransferSword.getTransferSword().getItemMeta().getDisplayName())) {

                int hate = (int) player.getInventory().getItem(EquipmentSlot.HAND).getItemMeta().getPersistentDataContainer().get(CustomKeys.TransferSwordHate.getKey(), CustomKeys.TransferSwordHate.getType());

                if(CooldownManager.getManager(player).hasCooldown(CustomKeys.TransferSwordCooldown.getKey()) && !(hate == 0)) {
                    player.sendMessage("Transfer Sword is on cooldown");
                    return;
                }

                if(hate < 10) {
                    //use opponents effects if they have them to add to hate
                    target.getActivePotionEffects().forEach(potionEffect -> {
                        int hate1 = (int) player.getInventory().getItem(EquipmentSlot.HAND).getItemMeta().getPersistentDataContainer().get(CustomKeys.TransferSwordHate.getKey(), CustomKeys.TransferSwordHate.getType());
                        if(hate1 < 10) {
                            hate1++;
                            player.getInventory().getItem(EquipmentSlot.HAND).getItemMeta();
                            ItemMeta meta = player.getInventory().getItem(EquipmentSlot.HAND).getItemMeta();
                            List<String> lore = new ArrayList<>();
                            lore.add(ChatColor.GRAY + "Stores potion effects as Hate");
                            lore.add(ChatColor.GRAY + "Hate: " + hate1 + "/10");
                            meta.getPersistentDataContainer().set(CustomKeys.TransferSwordHate.getKey(), CustomKeys.TransferSwordHate.getType(), hate1);
                            meta.setLore(lore);
                            player.getInventory().getItem(EquipmentSlot.HAND).setItemMeta(meta);
                            //remove potion effect
                            target.removePotionEffect(potionEffect.getType());
                        }
                    });
                }

                target.getPersistentDataContainer().set(CustomKeys.HasHate.getKey(), CustomKeys.HasHate.getType(), true);
                target.getPersistentDataContainer().set(CustomKeys.HateDuration.getKey(), CustomKeys.HateDuration.getType(), 20 * hate);
                new HateDamageRunnable(target, 20 * hate).runTaskTimer(Moon_s_arsenal.getInstance(), 0, 1);
                //set hate to 0
                ItemMeta meta = player.getInventory().getItem(EquipmentSlot.HAND).getItemMeta();
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + "Stores potion effects as Hate");
                lore.add(ChatColor.GRAY + "Hate: 0/10");
                meta.getPersistentDataContainer().set(CustomKeys.TransferSwordHate.getKey(), CustomKeys.TransferSwordHate.getType(), 0);
                meta.setLore(lore);
                player.getInventory().getItem(EquipmentSlot.HAND).setItemMeta(meta);
                CooldownManager.getManager(player).addCooldown(CustomKeys.TransferSwordCooldown.getKey(), 20 * 10);
            }
        }
    }
}
