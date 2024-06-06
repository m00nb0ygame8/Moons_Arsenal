package me.moonboygamer.moon_s_arsenal.weapons.abyssal_trident;

import me.moonboygamer.moon_s_arsenal.util.CooldownManager;
import me.moonboygamer.moon_s_arsenal.util.CustomKeys;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

import java.util.stream.Collectors;

public class AbyssalTridentListener implements Listener {
    //whirlpool when shot in water and shockwave on land
    @EventHandler
    public void onTridentThrow(ProjectileLaunchEvent event) {
        //if it was the abyssal trident add the persistant data tag 'is_abyssal_trident' to the trident
        if(event.getEntity() instanceof Trident && event.getEntity().getShooter() instanceof Player){
            Trident trident = (Trident) event.getEntity();
            Player player = (Player) event.getEntity().getShooter();
            if(player.getInventory().getItem(EquipmentSlot.HAND).getItemMeta().getPersistentDataContainer().has(CustomKeys.AbyssalTrident.getKey(), CustomKeys.AbyssalTrident.getType()) && (boolean) player.getInventory().getItem(EquipmentSlot.HAND).getItemMeta().getPersistentDataContainer().get(CustomKeys.AbyssalTrident.getKey(), CustomKeys.AbyssalTrident.getType()) == true) {
                trident.getPersistentDataContainer().set(CustomKeys.IsAbyssalTrident.getKey(), CustomKeys.IsAbyssalTrident.getType(), true) ;
            }
        }
    }
    @EventHandler
    public void onTridentLand(ProjectileHitEvent event) {
        if(!(event.getEntity() instanceof Trident)) return;
        if(event.getHitEntity() != null) {
            if(event.getHitEntity() instanceof Player) {
                Player player = (Player) event.getHitEntity();
                player.damage(5);
            }
        }
        if(event.getEntity().getPersistentDataContainer().has(CustomKeys.IsAbyssalTrident.getKey(), CustomKeys.IsAbyssalTrident.getType()) && CooldownManager.getManager((Player) event.getEntity().getShooter()).hasCooldown(CustomKeys.AbyssalTridentCooldown.getKey()) && (boolean) event.getEntity().getPersistentDataContainer().get(CustomKeys.IsAbyssalTrident.getKey(), CustomKeys.IsAbyssalTrident.getType()) == true) {
            if(event.getEntity().getShooter() instanceof Player) {
                Player player = (Player) event.getEntity().getShooter();
                player.sendMessage("Abyssal Trident is on cooldown!");
            }
            return;
        }
        if (event.getHitBlock() != null) {
            Location waterCheck = event.getHitBlock().getLocation().add(0, 1, 0);
            if(event.getEntity().getWorld().getBlockAt(waterCheck).getType().equals(Material.WATER) || (event.getEntity().getWorld().getBlockAt(event.getEntity().getLocation()).getType() == Material.WATER || event.getEntity().getWorld().getBlockAt(event.getEntity().getLocation()).getBlockData() instanceof Waterlogged && ((Waterlogged) event.getEntity().getWorld().getBlockAt(event.getEntity().getLocation()).getBlockData()).isWaterlogged()))  {
                //whirlpool
                //get nearby entities in 10 by 10 by 10 and pull them towards the epicenter
                event.getEntity().getWorld().getNearbyEntities(event.getHitBlock().getLocation(), 10, 10, 10).stream().filter(entity -> entity != event.getEntity().getShooter()).collect(Collectors.toList()).forEach(entity -> {
                    if(entity instanceof Player) {
                        Player player = (Player) entity;
                        Vector direction = event.getHitBlock().getLocation().toVector().subtract(player.getLocation().toVector());
                        player.setVelocity(direction.multiply(0.5));
                    }
                });
            } else {
                //shockwave
                //get nearby entities in 10 by 10 by 10 and launch them away from the epicenter
                event.getEntity().getWorld().getNearbyEntities(event.getHitBlock().getLocation(), 10, 10, 10).stream().filter(entity -> entity != event.getEntity().getShooter()).collect(Collectors.toList()).forEach(entity -> {
                    if(entity instanceof Player) {
                        Player player = (Player) entity;
                        player.setVelocity(new Vector(0, 3, 0));
                    }
                });
            }
            CooldownManager.getManager((Player) event.getEntity().getShooter()).addCooldown(CustomKeys.AbyssalTridentCooldown.getKey(), 20 * 10);
        }
    }
}
