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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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
        // Register Items
        try {
            ClassLoader cl = getClassLoader();
            Field[] fields = cl.getClass().getDeclaredFields();
            List<WeaponClasses> eclass = Arrays.asList(WeaponClasses.class.getEnumConstants());
            eclass.forEach(c -> {
                try {
                    c.getClazz().getClassLoader();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
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
    public enum WeaponClasses {
        ABYSSAL_TRIDENT(AbyssalTrident.class),
        ECHO_BOW(EchoBow.class),
        REFLECTIVE_CHESTPLATE(ReflectiveChestplate.class),
        SIRENS_CALL(SirensCall.class),
        TRANSFER_SWORD(TransferSword.class),
        CHORUS_PEARL(ChorusPearl.class);
        private Class<? extends ICustomItem> clazz;

        WeaponClasses(Class<? extends ICustomItem> clazz) {
            this.clazz = clazz;
        }

        public Class<? extends ICustomItem> getClazz() {
            return clazz;
        }
    }
}
