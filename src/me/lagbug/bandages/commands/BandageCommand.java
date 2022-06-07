package me.lagbug.bandages.commands;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.lagbug.bandages.Bandages;
import me.lagbug.bandages.commands.subcommands.Create;
import me.lagbug.bandages.commands.subcommands.Delete;
import me.lagbug.bandages.commands.subcommands.Give;
import me.lagbug.bandages.commands.subcommands.Types;

public class BandageCommand implements CommandExecutor {

	private final Bandages band = Bandages.getPlugin(Bandages.class);
	private final java.util.List<SubCommand> subCommands = new ArrayList<>();

	public BandageCommand() {
		this.subCommands.addAll(Arrays.asList(new Create(), new Delete(), new Give(), new Types()));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(band.getMessage(null, "general.no-console"));
			return false;
		}
		
		Player player = (Player) sender;

		if (args.length <= 0) {
			java.util.List<String> cmdNames= new ArrayList<>();
			subCommands.forEach(s -> cmdNames.add(s.getNames().get(0)));
			
			player.sendMessage(band.getMessage(player, "command.wrong-usage"));
			return false;
		}

		for (SubCommand subCommand : this.subCommands) {
			if (subCommand.getNames().contains(args[0].toLowerCase())) {
				
				if (!player.hasPermission(subCommand.getPermission()) || !player.hasPermission("bandages.*")) {
					player.sendMessage(band.getMessage(player, "command.no-permission"));
					return false;
				}
				
				subCommand.player = player;
				subCommand.onCommand(player, args);
				return false;
			}
		}
		
		
		return false;
	}

}
