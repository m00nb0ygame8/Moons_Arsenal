package me.moonboygamer.moon_s_arsenal.util;

import me.moonboygamer.moon_s_arsenal.Moon_s_arsenal;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

public enum CustomKeys {
    TransferSwordHate("transfer_sword_hate", PersistentDataType.INTEGER),
    HateDuration("hate_duration", PersistentDataType.INTEGER),
    HasHate("has_hate", PersistentDataType.BOOLEAN),
    ReflectiveChestplateCharges("reflective_chestplate_charges", PersistentDataType.INTEGER),
    EchoBowFirstProjectile("echo_bow_first_projectile", PersistentDataType.BOOLEAN),
    IsAbyssalTrident("is_abyssal_trident", PersistentDataType.BOOLEAN),
    IsChorusPearl("is_chorus_pearl", PersistentDataType.BOOLEAN),
    //Cooldowns
    AbyssalTridentCooldown("abyssal_trident_cooldown", PersistentDataType.INTEGER),
    EchoBowCooldown("echo_bow_cooldown", PersistentDataType.INTEGER),
    ReflectiveChestplateCooldown("reflective_chestplate_cooldown", PersistentDataType.INTEGER),
    TransferSwordCooldown("transfer_sword_cooldown", PersistentDataType.INTEGER),
    SirnesCallCooldown("sirens_call_cooldown", PersistentDataType.INTEGER),;
    private String key;
    private PersistentDataType type;

    CustomKeys(String key, PersistentDataType type) {
        this.key = key;
        this.type = type;
    }

    public NamespacedKey getKey() {
        return new NamespacedKey(Moon_s_arsenal.getInstance(), key);
    }

    public PersistentDataType getType() {
        return type;
    }
}
