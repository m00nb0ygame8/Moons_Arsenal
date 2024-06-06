package me.moonboygamer.moon_s_arsenal;

import me.moonboygamer.moon_s_arsenal.commands.CGCMD;
import me.moonboygamer.moon_s_arsenal.commands.CGCMDTC;
import me.moonboygamer.moon_s_arsenal.weapons.abyssal_trident.AbyssalTrident;
import me.moonboygamer.moon_s_arsenal.weapons.chorus_pearl.ChorusPearl;
import me.moonboygamer.moon_s_arsenal.weapons.echo_bow.EchoBow;
import me.moonboygamer.moon_s_arsenal.weapons.reflective_chestplate.ReflectiveChestplate;
import me.moonboygamer.moon_s_arsenal.weapons.siren_s_call.SirensCall;
import me.moonboygamer.moon_s_arsenal.weapons.transfersword.TransferSword;
import me.moonboygamer.moon_s_arsenal.util.CooldownManager;
import me.moonboygamer.moon_s_arsenal.util.ItemRegistry;
import me.moonboygamer.moon_s_arsenal.util.PlayerJoinEventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class Moon_s_arsenal extends JavaPlugin {
    public static ItemRegistry registry = new ItemRegistry();
    private static Moon_s_arsenal instance;

    @Override
    public void onEnable() {
        instance = this;
        //PJE
        getServer().getPluginManager().registerEvents(new PlayerJoinEventHandler(), this);
        //Cooldown Manager Init
        CooldownManager.init();
        //add items for loading
        AbyssalTrident.init();
        ChorusPearl.init();
        EchoBow.init();
        ReflectiveChestplate.init();
        SirensCall.init();
        TransferSword.init();
        //register commands & setup cooldowns
        getCommand("cgive").setExecutor(new CGCMD());
        getCommand("cgive").setTabCompleter(new CGCMDTC());
        getServer().getOnlinePlayers().forEach(CooldownManager::setupManager);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Moon_s_arsenal getInstance() {
        return instance;
    }
}
