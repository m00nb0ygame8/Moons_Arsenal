package me.moonboygamer.moon_s_arsenal.util;

import com.google.common.collect.ImmutableList;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemRegistry {
    ArrayList<ItemRegistryEntry> items = new ArrayList<>();
    public ItemRegistryEntry registerItem(ItemStack item, String registry_name) {
        ItemRegistryEntry entry = new ItemRegistryEntry();
        entry.item = item;
        entry.registry_name = registry_name;
        items.add(entry);
        return entry;
    }
    public ArrayList<ItemStack> getItems() {
        ArrayList<ItemStack> itemStacks = new ArrayList<>();
        for(ItemRegistryEntry entry : items) {
            itemStacks.add(entry.item);
        }
        return itemStacks;
    }
    public ArrayList<ItemRegistryEntry> getEntries() {
        return items;
    }
    public ItemStack getItem(ItemRegistryEntry fentry) {
        return items.stream().filter(entry -> entry.equals(fentry)).findFirst().get().item;
    }

    public static class ItemRegistryEntry {
        public ItemStack item;
        public String registry_name;
    }
}
