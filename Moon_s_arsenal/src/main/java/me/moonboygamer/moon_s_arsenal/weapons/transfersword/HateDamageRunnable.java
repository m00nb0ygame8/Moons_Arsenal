package me.moonboygamer.moon_s_arsenal.weapons.transfersword;

import me.moonboygamer.moon_s_arsenal.util.CustomKeys;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

public class HateDamageRunnable extends BukkitRunnable {
    private LivingEntity target;
    private int duration;

    public HateDamageRunnable(LivingEntity target, int duration) {
        this.target = target;
        this.duration = duration;
    }

    @Override
    public void run() {
        target.damage(1);
        duration--;
        if(duration <= 0) {
            target.getPersistentDataContainer().set(CustomKeys.HasHate.getKey(), CustomKeys.HasHate.getType(), false);
            cancel();
        }
    }
}
