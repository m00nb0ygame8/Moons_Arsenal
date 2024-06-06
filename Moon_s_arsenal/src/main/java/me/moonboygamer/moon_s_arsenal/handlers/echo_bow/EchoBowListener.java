package me.moonboygamer.moon_s_arsenal.handlers.echo_bow;

import me.moonboygamer.moon_s_arsenal.Moon_s_arsenal;
import me.moonboygamer.moon_s_arsenal.util.CooldownManager;
import me.moonboygamer.moon_s_arsenal.util.CustomKeys;
import me.moonboygamer.moon_s_arsenal.weapons.EchoBow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.EquipmentSlot;

public class EchoBowListener implements Listener {
    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if(player.getPlayer().getInventory().getItem(EquipmentSlot.HAND) == null) return;
            if(player.getInventory().getItem(EquipmentSlot.HAND).getItemMeta().getPersistentDataContainer().has(CustomKeys.EchoBow.getKey(), CustomKeys.EchoBow.getType()) && (boolean) player.getInventory().getItem(EquipmentSlot.HAND).getItemMeta().getPersistentDataContainer().get(CustomKeys.EchoBow.getKey(), CustomKeys.EchoBow.getType()) == true) {
                event.getProjectile().getPersistentDataContainer().set(CustomKeys.EchoBowFirstProjectile.getKey(), CustomKeys.EchoBowFirstProjectile.getType(), true);
            }
        }
    }
    @EventHandler
    public void onHit(ProjectileHitEvent event) {
        if(event.getEntity().getShooter() instanceof Player) {
            if(CooldownManager.getManager((Player) event.getEntity().getShooter()).hasCooldown(CustomKeys.EchoBowCooldown.getKey()) && event.getEntity() instanceof Arrow) {
                Player player = (Player) event.getEntity().getShooter();
                player.sendMessage("Echo Bow is on cooldown!");
                return;
            }
        } else {
            return;
        }
        //check if the arrow has the persistant key and it shoot a homing arrow after it hit the first one
        if(event.getEntity().getPersistentDataContainer().has(CustomKeys.EchoBowFirstProjectile.getKey(), CustomKeys.EchoBowFirstProjectile.getType())) {
            if(event.getEntity().getPersistentDataContainer().get(CustomKeys.EchoBowFirstProjectile.getKey(), CustomKeys.EchoBowFirstProjectile.getType()).equals(true)) {
                if(event.getHitEntity() instanceof LivingEntity) {
                    Arrow arrow = event.getEntity().getShooter().launchProjectile(Arrow.class);
                    LivingEntity target = (LivingEntity) event.getHitEntity();
                    if(target == event.getEntity().getShooter()) {
                        return;
                    }
                    new EchoBowArrowRunnable(arrow, target, (Player) event.getEntity().getShooter()).runTaskTimer(Moon_s_arsenal.getInstance(), 0, 1);
                }
            }
        }

    }
}
