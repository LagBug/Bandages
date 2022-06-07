package me.lagbug.bandages.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.lagbug.bandages.Bandages;
import me.lagbug.bandages.enums.ActionType;

public class Countdown {

	private final Bandages band = Bandages.getPlugin(Bandages.class);
	private final FileConfiguration types = band.getTypesFile();
	
	private Player player;
	private int time;
	private double health;
	private String key;

	public Countdown(Player player) {
		this.player = player;
	}

	@SuppressWarnings("unused")
	public Countdown start() {
		band.getBandaging().add(player);
		if (band.getMessage(null, "general.freeze").equals("enable")) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * time, 1));
		}

		BukkitTask task = new BukkitRunnable() {
			int rTime = time;

			@Override
			public void run() {

				if (this.rTime == time) {
					player.sendMessage(band.getMessage(player, "general.started-countdown"));
				}

				player.setSneaking(true);

				String rebanding = "";
				String passed = "";

				for (int i = 0; i < rTime; i++) { rebanding += band.getMessage(player, "actionbar.remaining-symbol"); }
				for (int i = time; i > rTime; i--) { passed += band.getMessage(player, "actionbar.passed-symbol"); }

				ActionBar.send(player, band.getMessage(player, "actionbar.process-message").replace("%remaining%", rebanding).replace("%passed%", passed));

				if (this.rTime == 0) {
					cancel();

					player.sendMessage(band.getMessage(player, "general.ended-countdown"));
					band.getBandaging().remove(player);
					player.setSneaking(false);

					try {
						player.setHealth(player.getHealth() + health);
					} catch (IllegalArgumentException ex) {
						player.setHealth(player.getHealth() + (player.getMaxHealth() - player.getHealth()));
					}

					for (String action : types.getStringList("types." + key + ".actions")) {
						Utils.sendAction(player, ActionType.valueOf(action.split(" ")[0].toUpperCase()), action);
					}

					return;
				}
				this.rTime--;
			}
		}.runTaskTimer(band, 0, 20);
		return this;
	}

	public Countdown setTotalTime(int time) {
		this.time = time;
		return this;
	}

	public Countdown setKey(String key) {
		this.key = key;
		return this;
	}

	public int getTotalTime() {
		return time;
	}

	public Countdown addHealth(double health) {
		this.health = health;
		return this;
	}

}
