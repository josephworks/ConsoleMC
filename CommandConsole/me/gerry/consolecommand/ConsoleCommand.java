package me.gerry.consolecommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ConsoleCommand extends JavaPlugin {
	private final ConsoleCommandPlayerListener playerListener = new ConsoleCommandPlayerListener(this);
	
	Configuration config;
	ConsoleCommandSender ccs;
	PluginDescriptionFile pdfFile;
	
	public void onEnable(){
		PluginManager pm = this.getServer().getPluginManager();
		pdfFile = this.getDescription();
        ccs = getServer().getConsoleSender();
        config = new Configuration(this);
        config.loadConfig();
        
        printMessage("version " + pdfFile.getVersion() + " by Schwarzer Zylinder is enabled.");
	}
	
	public void onDisable() {
		config.saveConfig();
		printMessage("is disabled.");
	}
	
	public void executecommand(String command){
		getServer().dispatchCommand(ccs, command);
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		return playerListener.onCommand(sender, command, label, args);
	}
	
	public boolean checkPermissions(CommandSender sender, String command){
		if (!(sender instanceof Player)) return true;
		Player player = (Player) sender;
		if(config.isOp()){
			if(player.isOp()){
				return true;
			}else{
				player.sendMessage("You are not an op.");
				return false;
			}
		}
		if(config.isPermissions()){
			if(player.hasPermission(command)){
				return true;
			}else{
				player.sendMessage("You don't have the permission to do this.");
				return false;
			}
		}
		return true;
	}

	public void printMessage(String message) {
		System.out.println("[ConsoleCommand] " + message);
	}
}