package me.lagbug.bandages.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

public abstract class SubCommand {
	
	private String[] names;
	private final String permission;
	protected Player player;

	public SubCommand(String names, String permission) {
		this.names = names.split(":");
		this.permission = permission;
	}

	public final List<String> getNames() {
		return Arrays.asList(this.names);
	}

	public final String getPermission() {
		return this.permission;
	}

	public abstract void onCommand(Player player, String[] args);
}
