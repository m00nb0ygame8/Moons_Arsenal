package me.moonboygamer.moon_s_arsenal.util;

import org.bukkit.scheduler.BukkitRunnable;

public class CooldownTickRunnable extends BukkitRunnable {
    @Override
    public void run() {
        CooldownManager.tick();
    }
}
