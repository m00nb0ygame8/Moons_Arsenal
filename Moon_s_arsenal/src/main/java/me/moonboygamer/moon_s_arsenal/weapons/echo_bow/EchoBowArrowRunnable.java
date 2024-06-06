package me.moonboygamer.moon_s_arsenal.weapons.echo_bow;

import me.moonboygamer.moon_s_arsenal.util.CooldownManager;
import me.moonboygamer.moon_s_arsenal.util.CustomKeys;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class EchoBowArrowRunnable extends BukkitRunnable {
    private Player shooter;
    private Entity arrow;
    private LivingEntity target;

    public EchoBowArrowRunnable(Entity arrow, LivingEntity target, Player shooter) {
        this.arrow = arrow;
        this.target = target;
        this.shooter = shooter;
    }

    @Override
    public void run() {
        if (target == null) {
            cancel();
        }

        if (arrow.isDead() || target.isDead()) {
            CooldownManager.getManager(shooter).addCooldown(CustomKeys.EchoBowCooldown.getKey(), 5 * 20);
            cancel();
            return;
        }

        Vector newVector = target.getBoundingBox().getCenter().subtract(arrow.getLocation().toVector()).normalize();

        arrow.setVelocity(newVector);
    }

}
