package me.gerry.consolecommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ConsoleCommandPlayerListener {
	
public static ConsoleCommand plugin;
	
	public ConsoleCommandPlayerListener(ConsoleCommand instance){
		plugin = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		String[] split = args;
		
		if(!label.equalsIgnoreCase("ccmd") || args.length < 1) return false;
		if(plugin.checkPermissions(sender, "cmd")){
			try{
				String message = split[0];
				int x = 1;
				while(x < split.length){
					message = message + " " + split[x];
					x++;
				}
				plugin.executecommand(message);
			}catch(Exception e){sender.sendMessage("Error: use /ccmd (command)");}
		}
		return true;
	}
}