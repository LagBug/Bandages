package me.lagbug.bandages.commands.subcommands;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.lagbug.bandages.Bandages;
import me.lagbug.bandages.commands.SubCommand;

public class Create extends SubCommand {

	public Create() {
		super("create:c", "bandages.create");
	}

	private final Bandages band = Bandages.getPlugin(Bandages.class);
	private final FileConfiguration types = band.getTypesFile();

	@Override
	public void onCommand(Player player, String[] args) {
		if (args.length <= 2) {
			player.sendMessage(band.getMessage(player, "command.create.wrong-usage"));
			return;
		}
		
		String name = "";
		
		for (int i = 2; i < args.length; i++) {
			name += args[i] + " ";
		}
		name = name.substring(0, name.length() - 1);
		String colorizedName = ChatColor.translateAlternateColorCodes('&', name);
		String stripedName = ChatColor.stripColor(colorizedName).toLowerCase().replace(" ", "_");
		String material = args[1].toUpperCase();
		
		types.set("types." + stripedName + ".name", name);
		
		types.set("types." + stripedName + ".additional-health-amount", 5);
		types.set("types." + stripedName + ".item", material);
		types.set("types." + stripedName + ".countdown-time", 5);
		types.set("types." + stripedName + ".add-potions", new ArrayList<>());
		types.set("types." + stripedName + ".remove-potions", new ArrayList<>());
		types.set("types." + stripedName + ".lore", Arrays.asList("&aThis is a custom lore.", "&aYou can add as many lines as you want."));
		
		band.saveFiles();
		player.sendMessage(band.getMessage(player, "command.create.success"));
		
		
	}
}
