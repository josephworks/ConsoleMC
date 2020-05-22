package net.josephworks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ConsoleMCPlayerListener {

    public static ConsoleMC plugin;

    public ConsoleMCPlayerListener(ConsoleMC instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!label.equalsIgnoreCase("ccmd") || args.length < 1) return false;
        if (plugin.checkPermissions(sender, "ccmd")) {
            try {
                StringBuilder message = new StringBuilder(args[0]);
                int x = 1;
                while (x < args.length) {
                    message.append(" ").append(args[x]);
                    x++;
                }
                plugin.executeCommand(message.toString());
            } catch (Exception e) {
                sender.sendMessage("Error: use /ccmd (command)");
            }
        }
        return true;
    }
}