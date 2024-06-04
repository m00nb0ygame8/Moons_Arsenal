package me.moonboygamer.moon_s_arsenal.util;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinEventHandler implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().getPersistentDataContainer().set(CustomKeys.HasHate.getKey(), CustomKeys.HasHate.getType(), false);
        //cooldowns
        if(CooldownManager.hasCooldownManager(event.getPlayer())) {
            CooldownManager.getManager(event.getPlayer()).resetCooldowns();
        } else {
            CooldownManager.setupManager(event.getPlayer());
        }
    }
}
