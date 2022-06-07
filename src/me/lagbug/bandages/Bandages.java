package me.lagbug.bandages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.clip.placeholderapi.PlaceholderAPI;
import me.lagbug.bandages.commands.BandageCommand;
import me.lagbug.bandages.events.OnPlayerInteract;
import me.lagbug.bandages.events.OnPlayerMove;
import me.lagbug.bandages.utils.FileUtils;
import me.lagbug.bandages.utils.Metrics;
import me.lagbug.bandages.utils.UpdateChecker;
import me.lagbug.bandages.utils.Utils;

public class Bandages extends JavaPlugin {	

	private final UpdateChecker updater = new UpdateChecker(this, 61286);
	
	private List<Player> bandaging = new ArrayList<>();
	private FileUtils fUtils;
	private FileConfiguration messagesFile, typesFile;
	
	@Override
	public void onEnable() {
		fUtils = new FileUtils();
		fUtils.initiateFiles();
		
		messagesFile = fUtils.getMessagesFile();
		typesFile = fUtils.getTypesFile();
		
		Bukkit.getPluginManager().registerEvents(new OnPlayerInteract(), this);
		Bukkit.getPluginManager().registerEvents(new OnPlayerMove(), this);
		getCommand("bandage").setExecutor(new BandageCommand());
	
		new Metrics(this);
		
		Bukkit.getConsoleSender().sendMessage("--------------------------------------------------");
		Bukkit.getConsoleSender().sendMessage(" [Bandages] Successfully enabled Bandages.");
		Bukkit.getConsoleSender().sendMessage(" [Bandages] Running version " + getDescription().getVersion());
		Bukkit.getConsoleSender().sendMessage("--------------------------------------------------");
		Bukkit.getConsoleSender().sendMessage(Utils.isPluginEnabled("PlaceholderAPI") ? " [Bandages] PlaceHolderAPI is found. Placeholders will work." : " [Bandages] PlaceHolderAPI could not be found. Placeholders will not work.");
		Bukkit.getConsoleSender().sendMessage("--------------------------------------------------");
		
		switch (updater.getResult()) {
		case ERROR:
			Bukkit.getConsoleSender().sendMessage(" --> Failed to check for updates.");
			break;
		case FOUND:
			Bukkit.getConsoleSender().sendMessage(" --> Found a new update! Download it using the following link:");
			Bukkit.getConsoleSender().sendMessage(" --> https://www.spigotmc.org/resources/bandages.61286/");
			break;
		case NOT_FOUND:
			Bukkit.getConsoleSender().sendMessage(" --> No updates were found, you are using the latest version.");
			break;
		case DEVELOPMENT:
			Bukkit.getConsoleSender().sendMessage(" --> You are running a development build, this might not be stable.");
			break;
		}
		Bukkit.getConsoleSender().sendMessage("--------------------------------------------------");
	}
	
	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("--------------------------------------------------");
		Bukkit.getConsoleSender().sendMessage(" [Bandages] Successfully disabled Bandages.");
		Bukkit.getConsoleSender().sendMessage("--------------------------------------------------");
	}

	public String getMessage(Player p, String path) {
		return ChatColor.translateAlternateColorCodes('&', Utils.isPluginEnabled("PlaceholderAPI") && p != null ?
				PlaceholderAPI.setPlaceholders(p, messagesFile.getString(path)).replace("%prefix%", messagesFile.getString("prefix")) : messagesFile.getString(path).replace("%prefix%", messagesFile.getString("prefix")));
	}
	
	public List<Player> getBandaging() {
		return this.bandaging;
	}
	
	public FileConfiguration getMessagesFile() {
		return this.messagesFile;
	}
	
	public FileConfiguration getTypesFile() {
		return this.typesFile;
	}
	
	public void saveFiles() {
		try {
			typesFile.save(fUtils.getTypesData());
			messagesFile.save(fUtils.getMessagesData());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}
}