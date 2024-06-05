package me.moonboygamer.moon_s_arsenal;

import me.moonboygamer.moon_s_arsenal.commands.CGCMD;
import me.moonboygamer.moon_s_arsenal.commands.CGCMDTC;
import me.moonboygamer.moon_s_arsenal.util.CooldownManager;
import me.moonboygamer.moon_s_arsenal.util.ICustomItem;
import me.moonboygamer.moon_s_arsenal.util.ItemRegistry;
import me.moonboygamer.moon_s_arsenal.util.PlayerJoinEventHandler;
import me.moonboygamer.moon_s_arsenal.weapons.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public final class Moon_s_arsenal extends JavaPlugin {
    public static ItemRegistry registry = new ItemRegistry();
    private static Moon_s_arsenal instance;
    public static ArrayList<Class<?>> toLoad = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;
        //PJE
        getServer().getPluginManager().registerEvents(new PlayerJoinEventHandler(), this);
        //Cooldown Manager Init
        CooldownManager.init();
        //add items for loading
        toLoad.add(AbyssalTrident.class);
        toLoad.add(ChorusPearl.class);
        toLoad.add(EchoBow.class);
        toLoad.add(ReflectiveChestplate.class);
        toLoad.add(SirensCall.class);
        toLoad.add(TransferSword.class);
        // Register Items
        try {
            ClassLoader cl = getClassLoader();
            Field[] fields = cl.getClass().getDeclaredFields();
            if(!toLoad.isEmpty()) {
                toLoad.forEach(clazz -> {
                    clazz.getClass().getClassLoader();
                });
            }
            var ref = new Object() {
                Field f = null;
            };
            for(Field f : fields) {
                if(f.getName().equals("classes")) {
                    ref.f = f;
                    ref.f.setAccessible(true);
                    f.setAccessible(true);
                    break;
                }
            }
            if(ref.f == null) {
                throw new RuntimeException("Could not find classes field");
            }
            ConcurrentHashMap<String, Class<?>> clazzes = (ConcurrentHashMap<String, Class<?>>) ref.f.get(cl);
            Collection<Class<?>> classes = clazzes.values();
            classes.forEach(c -> {
                try {
                    List<Class<?>> interfaces = Arrays.asList(c.getInterfaces());
                    if(interfaces.contains(ICustomItem.class)) {
                        ICustomItem item = (ICustomItem) c.newInstance();
                        item.init();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
