package me.moonboygamer.moon_s_arsenal.weapons.transfersword;

import me.moonboygamer.moon_s_arsenal.Moon_s_arsenal;
import me.moonboygamer.moon_s_arsenal.util.CustomKeys;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class TransferSword {
    public static void init() {
        Moon_s_arsenal.registry.registerItem(getTransferSword(), "transfer_sword");
        Bukkit.getServer().getPluginManager().registerEvents(new TransferSwordListener(), Moon_s_arsenal.getInstance());
    }
    public static ItemStack getTransferSword() {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Transfer Sword");
        meta.setUnbreakable(true);
        meta.getPersistentDataContainer().set(CustomKeys.TransferSwordHate.getKey(), CustomKeys.TransferSwordHate.getType(), 0);
        meta.setLore(List.of(ChatColor.GRAY + "Stores potion effects inside", ChatColor.GRAY + "Potions: 0/10"));
        meta.getPersistentDataContainer().set(CustomKeys.TransferSword.getKey(), CustomKeys.TransferSword.getType(), true);
        item.setItemMeta(meta);
        return item;
    }
}
