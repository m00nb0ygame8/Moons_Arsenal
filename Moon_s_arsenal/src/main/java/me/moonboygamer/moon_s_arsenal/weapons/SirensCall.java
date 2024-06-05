package me.moonboygamer.moon_s_arsenal.weapons;

import me.moonboygamer.moon_s_arsenal.Moon_s_arsenal;
import me.moonboygamer.moon_s_arsenal.handlers.siren_s_call.SirensCallListener;
import me.moonboygamer.moon_s_arsenal.util.CustomKeys;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SirensCall {
    public static void init() {
        Moon_s_arsenal.registry.registerItem(getSirensCall(), "sirens_call");
        Bukkit.getServer().getPluginManager().registerEvents(new SirensCallListener(), Moon_s_arsenal.getInstance());
    }
    public static ItemStack getSirensCall() {
        ItemStack item = new ItemStack(Material.EMERALD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_RED + "Siren\'s Call");
        meta.setUnbreakable(true);
        meta.getPersistentDataContainer().set(CustomKeys.SirensCall.getKey(), CustomKeys.SirensCall.getType(), true);
        item.setItemMeta(meta);
        return item;
    }
}
