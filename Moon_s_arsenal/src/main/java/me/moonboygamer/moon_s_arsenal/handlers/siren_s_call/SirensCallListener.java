package me.moonboygamer.moon_s_arsenal.handlers.siren_s_call;

import me.moonboygamer.moon_s_arsenal.Moon_s_arsenal;
import me.moonboygamer.moon_s_arsenal.util.CooldownManager;
import me.moonboygamer.moon_s_arsenal.util.CustomKeys;
import me.moonboygamer.moon_s_arsenal.weapons.SirensCall;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SirensCallListener implements Listener {
    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(event.getPlayer().getInventory().getItem(EquipmentSlot.HAND).getItemMeta().getDisplayName().equals(SirensCall.getSirensCall().getItemMeta().getDisplayName()) && event.getPlayer().getInventory().getItem(EquipmentSlot.HAND).getItemMeta().getPersistentDataContainer().has(CustomKeys.SirensCall.getKey(), CustomKeys.SirensCall.getType()) && (boolean) event.getPlayer().getInventory().getItem(EquipmentSlot.HAND).getItemMeta().getPersistentDataContainer().get(CustomKeys.SirensCall.getKey(), CustomKeys.SirensCall.getType()) == true) {
                if(CooldownManager.getManager(event.getPlayer()).hasCooldown(CustomKeys.SirnesCallCooldown.getKey())) {
                    event.getPlayer().sendMessage("Siren's Call is on cooldown");
                    return;
                }
                event.getPlayer().getWorld().getNearbyEntities(event.getPlayer().getLocation(), 30, 30, 30).stream().filter(entity -> entity instanceof LivingEntity && entity != event.getPlayer()).forEach(entity -> {
                    //force them to look at the location used
                    new SirensCallForceLook((LivingEntity) entity, event.getPlayer().getLocation(), 5*20).runTaskTimer(Moon_s_arsenal.getInstance(), 0, 1);
                    ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5*20, 255));
                    ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 5*20, 250));
                    ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 5*20, 250));
                    ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 5*20, 250));
                });
                CooldownManager.getManager(event.getPlayer()).addCooldown(CustomKeys.SirnesCallCooldown.getKey(), 30*20);
            }
        }
    }
}
