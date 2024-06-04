package me.moonboygamer.moon_s_arsenal.weapons;

import me.moonboygamer.moon_s_arsenal.Moon_s_arsenal;
import me.moonboygamer.moon_s_arsenal.handlers.abyssal_trident.AbyssalTridentListener;
import me.moonboygamer.moon_s_arsenal.util.ICustomItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AbyssalTrident implements ICustomItem {
    public void init() {
        Moon_s_arsenal.registry.registerItem(getAbyssalTrident(), "abyssal_trident");
        Bukkit.getServer().getPluginManager().registerEvents(new AbyssalTridentListener(), Moon_s_arsenal.getInstance());
    }
    public static ItemStack getAbyssalTrident() {
        ItemStack item = new ItemStack(Material.TRIDENT);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "Abyssal Trident");
        meta.addEnchant(Enchantment.LOYALTY, 3, true);
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        return item;
    }
}