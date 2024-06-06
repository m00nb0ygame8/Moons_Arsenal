package me.moonboygamer.moon_s_arsenal.weapons.reflective_chestplate;

import java.util.List;
import me.moonboygamer.moon_s_arsenal.Moon_s_arsenal;
import me.moonboygamer.moon_s_arsenal.util.CustomKeys;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ReflectiveChestplate {
    public static void init() {
        Moon_s_arsenal.registry.registerItem(getRefectiveChestplate(), "reflective_chestplate");
        Bukkit.getServer().getPluginManager().registerEvents(new ReflectiveChestplateListener(), Moon_s_arsenal.getInstance());
    }
    public static ItemStack getRefectiveChestplate() {
        ItemStack item = new ItemStack(Material.IRON_CHESTPLATE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Reflective Chestplate");
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 6, true);
        meta.setUnbreakable(true);
        meta.getPersistentDataContainer().set(CustomKeys.ReflectiveChestplateCharges.getKey(), CustomKeys.ReflectiveChestplateCharges.getType(), 0);
        meta.setLore(List.of(ChatColor.GRAY + "Reflects damage back to the attacker", ChatColor.GRAY + "Charges: 0/4"));
        meta.getPersistentDataContainer().set(CustomKeys.ReflectiveChestplate.getKey(), CustomKeys.ReflectiveChestplate.getType(), true);
        item.setItemMeta(meta);
        return item;
    }
}
