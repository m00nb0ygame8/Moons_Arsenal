package me.moonboygamer.moon_s_arsenal.util;

import me.moonboygamer.moon_s_arsenal.Moon_s_arsenal;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CooldownManager {
    private static ArrayList<CooldownManagerInstance> managers = new ArrayList<>();
    public static void init() {
        new CooldownTickRunnable().runTaskTimer(Moon_s_arsenal.getInstance(), 0, 1);
    }

    public static CooldownManagerInstance getManager(Player player) {
        for(CooldownManagerInstance manager : managers) {
            if(manager.player.equals(player)) {
                return manager;
            }
        }
        CooldownManagerInstance manager = new CooldownManagerInstance(player);
        managers.add(manager);
        return manager;
    }
    public static boolean hasCooldown(Player player, NamespacedKey key) {
        CooldownManagerInstance manager = getManager(player);
        return manager.getCooldown(key) > 0;
    }
    public static boolean hasCooldownManager(Player player) {
        for(CooldownManagerInstance manager : managers) {
            if(manager.player.equals(player)) {
                return true;
            }
        }
        return false;
    }
    public static void tick() {
        for(CooldownManagerInstance manager : managers) {
            manager.tick();
        }
    }
    public static void setupManager(Player player) {
        managers.add(new CooldownManagerInstance(player));
    }
    public static class CooldownManagerInstance {
        private Player player;
        private ArrayList<Pair<NamespacedKey, Integer>> cooldowns = new ArrayList<>();

        public CooldownManagerInstance(Player player) {
            this.player = player;
            populateCooldowns();
        }

        public void addCooldown(NamespacedKey key, int duration) {
            cooldowns.add(new Pair<>(key, duration));
        }
        public int getCooldown(NamespacedKey key) {
            for(Pair<NamespacedKey, Integer> pair : cooldowns) {
                if(pair.getFirst().equals(key)) {
                    return pair.getSecond();
                }
            }
            return 0;
        }
        public boolean hasCooldown(NamespacedKey key) {
            for(Pair<NamespacedKey, Integer> pair : cooldowns) {
                if(pair.getFirst().equals(key)) {
                    return pair.getSecond() > 0;
                }
            }
            return false;
        }
        public void tick() {
            ArrayList<Pair<NamespacedKey, Integer>> toRemove = new ArrayList<>();
            for(Pair<NamespacedKey, Integer> pair : cooldowns) {
                pair.second--;
                if(pair.second <= 0) {
                    toRemove.add(pair);
                }
            }
            cooldowns.removeAll(toRemove);
        }
        public void resetCooldown(NamespacedKey key) {
            for(Pair<NamespacedKey, Integer> pair : cooldowns) {
                if(pair.getFirst().equals(key)) {
                    pair.second = 0;
                }
            }
        }
        public void resetCooldowns() {
            cooldowns.forEach(pair -> {
                pair.second = 0;
            });
        }
        private void populateCooldowns() {
            //populate cooldowns
            cooldowns.add(new Pair<>(CustomKeys.AbyssalTridentCooldown.getKey(), 0));
            cooldowns.add(new Pair<>(CustomKeys.EchoBowCooldown.getKey(), 0));
            cooldowns.add(new Pair<>(CustomKeys.ReflectiveChestplateCooldown.getKey(), 0));
            cooldowns.add(new Pair<>(CustomKeys.TransferSwordCooldown.getKey(), 0));
            cooldowns.add(new Pair<>(CustomKeys.SirnesCallCooldown.getKey(), 0));
        }
    }
}
