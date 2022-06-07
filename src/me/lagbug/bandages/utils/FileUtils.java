package me.lagbug.bandages.utils;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import me.lagbug.bandages.Bandages;

public class FileUtils {

	private final Bandages band = Bandages.getPlugin(Bandages.class);
	
	private File messagesFile, typesFile;
	private YamlConfiguration modifyMessages, modifyTypes;

	public void initiateFiles() {
		messagesFile = new File(band.getDataFolder(), "messages.yml");
		if (!messagesFile.exists()) {
			band.saveResource("messages.yml", false);
		}
		modifyMessages = YamlConfiguration.loadConfiguration(messagesFile);
		
		typesFile = new File(band.getDataFolder(), "types.yml");
		if (!typesFile.exists()) {
			band.saveResource("types.yml", false);
		}
		modifyTypes = YamlConfiguration.loadConfiguration(typesFile);
		
	}
	
	public YamlConfiguration getMessagesFile() { return modifyMessages; }
	public File getMessagesData() { return messagesFile; }
	
	public YamlConfiguration getTypesFile() { return modifyTypes; }
	public File getTypesData() { return typesFile; }
}
