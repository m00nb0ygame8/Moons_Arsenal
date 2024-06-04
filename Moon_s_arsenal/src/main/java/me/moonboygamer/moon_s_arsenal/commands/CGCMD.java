package me.moonboygamer.moon_s_arsenal.commands;

import me.moonboygamer.moon_s_arsenal.Moon_s_arsenal;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class CGCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player) {
            Moon_s_arsenal.registry.getEntries().forEach(entry -> {
                if(args[0].equals(entry.registry_name)) {
                    Player player = (Player) commandSender;
                    player.getInventory().addItem(entry.item);
                }
            });
            return true;
        }
        return false;
    }
}
