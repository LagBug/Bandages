package me.lagbug.bandages.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.lagbug.bandages.Bandages;

public class OnPlayerMove implements Listener {

	private final Bandages band = Bandages.getPlugin(Bandages.class);
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player player = e.getPlayer();

		if (!band.getMessage(null, "general.freeze").equals("enable") || !band.getBandaging().contains(player)) {
			return;
		}
			
		Location from = e.getFrom();
		Location to = e.getTo();

		if (from.getX() != to.getX() || from.getY() != to.getY() || from.getZ() != to.getZ()) {
			player.teleport(from.setDirection(to.getDirection()));
		}
		

	}
}
