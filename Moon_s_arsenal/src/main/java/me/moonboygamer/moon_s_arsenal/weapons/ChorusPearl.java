package me.moonboygamer.moon_s_arsenal.weapons;

import me.moonboygamer.moon_s_arsenal.Moon_s_arsenal;
import me.moonboygamer.moon_s_arsenal.handlers.chorus_pearl.ChorusPearlListener;
import me.moonboygamer.moon_s_arsenal.handlers.siren_s_call.SirensCallListener;
import me.moonboygamer.moon_s_arsenal.util.ICustomItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ChorusPearl implements ICustomItem {
    public void init() {
        Moon_s_arsenal.registry.registerItem(getChorusPearl(), "chorus_pearl");
        Bukkit.getServer().getPluginManager().registerEvents(new ChorusPearlListener(), Moon_s_arsenal.getInstance());
    }
    public static ItemStack getChorusPearl() {
        ItemStack item = new ItemStack(Material.ENDER_PEARL);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "Chorus Pearl");
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        item.setAmount(16);
        return item;
    }
}
