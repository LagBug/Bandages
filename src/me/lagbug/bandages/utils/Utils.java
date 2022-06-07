package me.lagbug.bandages.utils;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.clip.placeholderapi.PlaceholderAPI;
import me.lagbug.bandages.enums.ActionType;
import net.md_5.bungee.api.ChatColor;

public class Utils {

	public static double checkSimilarity(String s1, String s2) {
		String longer = s1, shorter = s2;
		if (s1.length() < s2.length()) {
			longer = s2;
			shorter = s1;
		}
		int longerLength = longer.length();
		if (longerLength == 0) {
			return 1.0;
		}

		return (longerLength - editDistance(longer, shorter)) / (double) longerLength;
	}

	public static int editDistance(String s1, String s2) {
		s1 = s1.toLowerCase();
		s2 = s2.toLowerCase();

		int[] costs = new int[s2.length() + 1];
		for (int i = 0; i <= s1.length(); i++) {
			int lastValue = i;
			for (int j = 0; j <= s2.length(); j++) {
				if (i == 0)
					costs[j] = j;
				else {
					if (j > 0) {
						int newValue = costs[j - 1];
						if (s1.charAt(i - 1) != s2.charAt(j - 1))
							newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
						costs[j - 1] = lastValue;
						lastValue = newValue;
					}
				}
			}
			if (i > 0)
				costs[s2.length()] = lastValue;
		}
		return costs[s2.length()];
	}

	public static boolean isPluginEnabled(String plugin) {
		return Bukkit.getPluginManager().getPlugin(plugin) != null && Bukkit.getPluginManager().getPlugin(plugin).isEnabled();
	}


	@SuppressWarnings("deprecation")
	public static void sendAction(Player player, ActionType action, String msg) {
		msg = ChatColor.translateAlternateColorCodes('&', msg.replace(action.toString().toLowerCase() + " ", ""));
		
		switch (action) {

		case PLAYERMSG:
			player.sendMessage(isPluginEnabled("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(player, msg) : msg);
			break;
		case PLAYERCMD:
			player.performCommand(isPluginEnabled("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(player, msg) : msg);
			break;
		case CONSOLECMD:
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), isPluginEnabled("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(player, msg) : msg);
			break;
		case ADDPOTION:
			String[] typeR = msg.split(":");

			PotionEffectType typeA = PotionEffectType.getByName(typeR[0].toUpperCase());
			int duration = Integer.parseInt(typeR[1]) * 20;
			int amplifier = Integer.parseInt(typeR[2]);
			player.addPotionEffect(new PotionEffect(typeA, duration, amplifier));
			break;
		case REMOVEPOTION:
			PotionEffectType type = PotionEffectType.getByName(msg.toUpperCase());
			for (PotionEffect effect : player.getActivePotionEffects()) {
				if (effect.getType().equals(type)) {
					player.removePotionEffect(type);
				}
			}
			break;
		case PLAYPARTICLE:
			for (int i = 0; i < 5; i++) {
				player.playEffect(player.getLocation().add(randomNumber(3), randomNumber(5), randomNumber(3)), Effect.valueOf(msg.toUpperCase()), 10);	
			}
			
			break;
		}
	}
	
	public static int randomNumber(int index) {
		return new Random().nextInt(index);
	}
}
