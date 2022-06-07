package me.lagbug.bandages.commands.subcommands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.lagbug.bandages.Bandages;
import me.lagbug.bandages.commands.SubCommand;
import me.lagbug.bandages.utils.ItemBuilder;
import me.lagbug.bandages.utils.Utils;

public class Give extends SubCommand {

	public Give() {
		super("give:g", "bandages.give");
	}

	private final Bandages band = Bandages.getPlugin(Bandages.class);
	private final FileConfiguration types = band.getTypesFile();

	@Override
	public void onCommand(Player player, String[] args) {
		boolean found = false;
		if (args.length <= 3) {
			player.sendMessage(band.getMessage(player, "command.give.wrong-usage"));
			return;
		}

		Player target = Bukkit.getPlayer(args[1]);
		String type = args[2];
		int amount = Integer.parseInt(args[3]);

		
		if (target == null) {
			player.sendMessage(band.getMessage(player, "command.give.player-null"));
			return;
		}
		
		for (String s : types.getConfigurationSection("types").getKeys(false)) {

			String name = null;
			try {
				name = ChatColor.translateAlternateColorCodes('&', types.getString("types." + s + ".name"));
			} catch (NullPointerException e) {
				break;
			}

			if (type.equalsIgnoreCase(ChatColor.stripColor(name).replace(" ", "_"))) {
				Material material = Material.valueOf(types.getString("types." + s + ".item").split(";")[0].toUpperCase());
				List<String> lore = types.getStringList("types." + s + ".lore");
				byte data = Byte.parseByte(types.getString("types." + s + ".item").split(";")[1]);

				target.getInventory().addItem(new ItemBuilder(material, amount, data).setDisplayName(name).setLore(lore).create());
				player.sendMessage(band.getMessage(player, "command.give.success"));
				found = true;
				break;
			}

		}

		if (!found) {
			player.sendMessage(band.getMessage(player, "command.not-found"));

			for (String s : types.getConfigurationSection("types").getKeys(false)) {
				if (Utils.checkSimilarity(s, type) >= 0.5) {
					player.sendMessage(band.getMessage(player, "command.suggestion").replace("%suggestion%", s));
					break;
				}
			}

		}
		
	}

}
