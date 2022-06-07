package me.lagbug.bandages.commands.subcommands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.lagbug.bandages.Bandages;
import me.lagbug.bandages.commands.SubCommand;

public class Types extends SubCommand {

	public Types() {
		super("types:t", "bandages.types");
	}

	private final Bandages band = Bandages.getPlugin(Bandages.class);
	private final FileConfiguration types = band.getTypesFile();

	@Override
	public void onCommand(Player player, String[] args) {
		List<String> typesList = new ArrayList<>();
		
		for (String s : types.getConfigurationSection("types").getKeys(false)) {
			String name = ChatColor.translateAlternateColorCodes('&', types.getString("types." + s + ".name"));
			typesList.add(ChatColor.stripColor(name.toLowerCase().replace(" ", "_")));
		}
		
		player.sendMessage(band.getMessage(player, "command.types.message").replace("%types%", typesList.toString()));
	}
}
