package me.moonboygamer.moon_s_arsenal.handlers.chorus_pearl;

import me.moonboygamer.moon_s_arsenal.util.CustomKeys;
import me.moonboygamer.moon_s_arsenal.weapons.ChorusPearl;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ChorusPearlListener implements Listener {
    @EventHandler
    public void onChorusPearlThrow(ProjectileLaunchEvent event) {
        if(event.getEntity().getShooter() instanceof Player) {
            Player player = (Player) event.getEntity().getShooter();
            if(player.getPlayer().getInventory().getItem(EquipmentSlot.HAND) == null) return;
            if(player.getInventory().getItem(EquipmentSlot.HAND).getItemMeta().getDisplayName().equals(ChorusPearl.getChorusPearl().getItemMeta().getDisplayName()) && player.getInventory().getItem(EquipmentSlot.HAND).getItemMeta().getPersistentDataContainer().has(CustomKeys.ChorusPearl.getKey(), CustomKeys.ChorusPearl.getType()) && (boolean) player.getInventory().getItem(EquipmentSlot.HAND).getItemMeta().getPersistentDataContainer().get(CustomKeys.ChorusPearl.getKey(), CustomKeys.ChorusPearl.getType()) == true){
                event.getEntity().getPersistentDataContainer().set(CustomKeys.IsChorusPearl.getKey(), CustomKeys.IsChorusPearl.getType(), true);
            }
        }
    }
    @EventHandler
    public void onChorusPearlLand(ProjectileHitEvent event) {
        if(event.getEntity().getPersistentDataContainer().has(CustomKeys.IsChorusPearl.getKey(), CustomKeys.IsChorusPearl.getType()) && (boolean) event.getEntity().getPersistentDataContainer().get(CustomKeys.IsChorusPearl.getKey(), CustomKeys.IsChorusPearl.getType()) == true) {
            //tp them to the location
            if(event.getEntity().getShooter() instanceof Player) {
                Player player = (Player) event.getEntity().getShooter();
                player.teleport(event.getHitBlock().getLocation());
                //randomly tp the player in a 15 by 15 by 15 area but must be above ground
                boolean foundGoodLocation = false;
                while(!foundGoodLocation) {
                    Location randomLocation = new Location(event.getEntity().getWorld(), event.getHitBlock().getLocation().getX() + Math.random() * 20, event.getHitBlock().getLocation().getY(), event.getHitBlock().getLocation().getZ() + Math.random() * 20);
                    if ((randomLocation.getBlock().isEmpty() && randomLocation.getBlock().getRelative(0, 1, 0).isEmpty() && !randomLocation.getBlock().getRelative(0, -1, 0).isEmpty()) ||(randomLocation.getBlock().getType() == Material.WATER && randomLocation.getBlock().getRelative(0, 1, 0).getType() == Material.WATER && !randomLocation.getBlock().getRelative(0, -1, 0).isEmpty())) {
                        player.teleport(randomLocation);
                        foundGoodLocation = true;

                    }
                }
                event.getEntity().remove();
            }
        }
    }
}
