package me.moonboygamer.moon_s_arsenal.handlers.siren_s_call;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

public class SirensCallForceLook extends BukkitRunnable {
    private LivingEntity player;
    private Location location;
    private int duration;
    private int ticks = 0;

    public SirensCallForceLook(LivingEntity player, Location lookatloc, int duration) {
        this.player = player;
        this.location = lookatloc;
        this.duration = duration;
    }

    @Override
    public void run() {
        if(player.isDead()) {
            cancel();
            return;
        }
        if (ticks >= duration) {
            cancel();
            return;
        }
        lookAt(player.getLocation(), location, player);
        ticks++;
    }

    private static Location lookAt(Location loc, Location lookat, LivingEntity player) {
        //Clone the loc to prevent applied changes to the input loc
        loc = loc.clone();

        // Values of change in distance (make it relative)
        double dx = lookat.getX() - loc.getX();
        double dy = lookat.getY() - loc.getY();
        double dz = lookat.getZ() - loc.getZ();

        // Set yaw
        if (dx != 0) {
            // Set yaw start value based on dx
            if (dx < 0) {
                loc.setYaw((float) (1.5 * Math.PI));
            } else {
                loc.setYaw((float) (0.5 * Math.PI));
            }
            loc.setYaw((float) loc.getYaw() - (float) Math.atan(dz / dx));
        } else if (dz < 0) {
            loc.setYaw((float) Math.PI);
        }

        // Get the distance from dx/dz
        double dxz = Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2));

        // Set pitch
        loc.setPitch((float) -Math.atan(dy / dxz));

        // Set values, convert to degrees (invert the yaw since Bukkit uses a different yaw dimension format)
        loc.setYaw(-loc.getYaw() * 180f / (float) Math.PI);
        loc.setPitch(loc.getPitch() * 180f / (float) Math.PI);
        player.teleport(loc);
        return loc;
    }
}
