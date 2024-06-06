package me.moonboygamer.moon_s_arsenal.weapons.echo_bow;

import me.moonboygamer.moon_s_arsenal.Moon_s_arsenal;
import me.moonboygamer.moon_s_arsenal.util.CustomKeys;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EchoBow {
    public static void init() {
        Moon_s_arsenal.registry.registerItem(getEchoBow(), "echo_bow");
        Bukkit.getServer().getPluginManager().registerEvents(new EchoBowListener(), Moon_s_arsenal.getInstance());
    }
    public static ItemStack getEchoBow() {
        ItemStack item = new ItemStack(Material.BOW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_AQUA + "Echo Bow");
        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.setUnbreakable(true);
        meta.getPersistentDataContainer().set(CustomKeys.EchoBow.getKey(), CustomKeys.EchoBow.getType(), true);
        item.setItemMeta(meta);
        return item;
    }
}
