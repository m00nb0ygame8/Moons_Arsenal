package me.moonboygamer.moon_s_arsenal.weapons.abyssal_trident;

import me.moonboygamer.moon_s_arsenal.Moon_s_arsenal;
import me.moonboygamer.moon_s_arsenal.util.CustomKeys;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AbyssalTrident {
    public static void init() {
        Moon_s_arsenal.registry.registerItem(getAbyssalTrident(), "abyssal_trident");
        Bukkit.getServer().getPluginManager().registerEvents(new AbyssalTridentListener(), Moon_s_arsenal.getInstance());
    }
    public static ItemStack getAbyssalTrident() {
        ItemStack item = new ItemStack(Material.TRIDENT);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "Abyssal Trident");
        meta.addEnchant(Enchantment.LOYALTY, 3, true);
        meta.setUnbreakable(true);
        meta.getPersistentDataContainer().set(CustomKeys.IsAbyssalTrident.getKey(), CustomKeys.IsAbyssalTrident.getType(), true);
        item.setItemMeta(meta);
        return item;
    }
}
