package me.lagbug.bandages.events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.lagbug.bandages.Bandages;
import me.lagbug.bandages.utils.Countdown;

public class OnPlayerInteract implements Listener {

	private final Bandages band = Bandages.getPlugin(Bandages.class);
	private final FileConfiguration types = band.getTypesFile();
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {		
		Player player = e.getPlayer();

		if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && e.getItem() != null && !e.getItem().getType().equals(Material.AIR) && e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasDisplayName()) {
			for (String s : types.getConfigurationSection("types").getKeys(false)) {
				String name = ChatColor.translateAlternateColorCodes('&', types.getString("types." + s + ".name"));

				if (e.getItem().getItemMeta().getDisplayName().equals(name)) {
					if (!isReady(player)) {
						e.setCancelled(true);
						return;
					}

					double health = types.getDouble("types." + s + ".additional-health-amount");
					int time = types.getInt("types." + s + ".countdown-time");
					int amount = e.getItem().getAmount();

					double x = player.getLocation().getX();
					double y = Math.floor(player.getLocation().getY());
					double z = player.getLocation().getZ();

					player.teleport(new Location(player.getWorld(), x, y, z, player.getLocation().getYaw(), player.getLocation().getPitch()));
					new Countdown(player).setTotalTime(time).addHealth(health).setKey(s).start();

					switch (amount) {
					case 1:
						player.setItemInHand(new ItemStack(Material.AIR));
						break;
					default:
						e.getItem().setAmount(amount - 1);
						break;

					}

					if (band.getMessage(null, "general.invincible").equals("enable")) {
						e.setCancelled(true);	
					}
					
					break;
				}
			}

		}

	}

	public boolean isReady(Player player) {
		if (band.getMessage(null, "general.freeze").equals("enable") && (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.AIR) || player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.LONG_GRASS))) {
			player.sendMessage(band.getMessage(player, "errors.air"));
			return false;
		}

		if (band.getBandaging().contains(player)) {
			player.sendMessage(band.getMessage(player, "errors.twice"));
			return false;
		}

		return true;
	}

}