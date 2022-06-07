package me.lagbug.bandages.commands.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.lagbug.bandages.Bandages;
import me.lagbug.bandages.commands.SubCommand;
import me.lagbug.bandages.utils.Utils;

public class Delete extends SubCommand {

	public Delete() {
		super("delete:d", "bandages.delete");
	}

	private final Bandages band = Bandages.getPlugin(Bandages.class);
	private final FileConfiguration types = band.getTypesFile();

	@Override
	public void onCommand(Player player, String[] args) {
		boolean found = false;
		
		if (args.length <= 1) {
			player.sendMessage(band.getMessage(player, "command.delete.wrong-usage"));
			return;
		}

		String toDelete = args[1];

		for (String s : types.getConfigurationSection("types").getKeys(false)) {
			String name = null;
			
			try {
				name = ChatColor.translateAlternateColorCodes('&', types.getString("types." + s + ".name"));
			} catch (NullPointerException e) {
				break;
			}
			
			if (toDelete.equalsIgnoreCase(ChatColor.stripColor(name).replace(" ", "_"))) {
				types.set("types." + s, null);
				band.saveFiles();
				player.sendMessage(band.getMessage(player, "command.delete.success"));
				found = true;
				break;
			}

		}

		if (!found) {
			player.sendMessage(band.getMessage(player, "command.not-found"));

			for (String s : types.getConfigurationSection("types").getKeys(false)) {
				if (Utils.checkSimilarity(s, toDelete) >= 0.5) {
					player.sendMessage(band.getMessage(player, "command.suggestion").replace("%suggestion%", s));
					break;
				}
			}

		}
		
	}

}
