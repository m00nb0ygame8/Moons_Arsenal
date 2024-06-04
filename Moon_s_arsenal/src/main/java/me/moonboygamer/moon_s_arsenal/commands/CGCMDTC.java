package me.moonboygamer.moon_s_arsenal.commands;

import me.moonboygamer.moon_s_arsenal.Moon_s_arsenal;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class CGCMDTC implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if(args.length == 1) {
            List<String> list = new ArrayList<>();
            Moon_s_arsenal.registry.getEntries().forEach(entry -> {
                list.add(entry.registry_name);
            });
            return list;
        }
        return null;
    }
}
