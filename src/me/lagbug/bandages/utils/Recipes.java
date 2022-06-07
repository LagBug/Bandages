package me.lagbug.bandages.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import me.lagbug.bandages.Bandages;

public class Recipes {
	
	private final Bandages band = Bandages.getPlugin(Bandages.class);
	private final FileConfiguration types = band.getTypesFile();
	
	public void loadRecipes() {	
		for (String key : types.getConfigurationSection("types").getKeys(false)) {
			
			ItemStack item = new ItemBuilder(Material.valueOf(getString(key, "item")), 1, (byte) 0).setDisplayName(getString(key, "name")).setLore(getStringList(key, "lore")).create();

			ShapedRecipe recipe = new ShapedRecipe(item);
			char[] recipeLetters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
			String[] ingredientNames = {
					getMaterialAtIndex(0, key).toString(),
					getMaterialAtIndex(1, key).toString(),
					getMaterialAtIndex(2, key).toString(),
					getMaterialAtIndex(3, key).toString(),
					getMaterialAtIndex(4, key).toString(),
					getMaterialAtIndex(5, key).toString(),
					getMaterialAtIndex(6, key).toString(),
					getMaterialAtIndex(7, key).toString(),
					getMaterialAtIndex(8, key).toString()		
			};
			for (int x = 0; x < ingredientNames.length; x++) {
				if (ingredientNames[x].equals("AIR")) {
					recipeLetters[x] = ' ';
				}
			}
			
			
			recipe.shape((""+recipeLetters[0]+recipeLetters[1]+recipeLetters[2]), (""+recipeLetters[3]+recipeLetters[4]+recipeLetters[5]), (""+recipeLetters[6]+recipeLetters[7]+recipeLetters[8]));
			
			for (char c : recipeLetters) {
				
				switch (c) {
				
				case 'A':
					recipe.setIngredient('A', getMaterialAtIndex(0, key));
				case 'B':
					recipe.setIngredient('B', getMaterialAtIndex(1, key));
				case 'C':
					recipe.setIngredient('C', getMaterialAtIndex(2, key));
				case 'D':
					recipe.setIngredient('D', getMaterialAtIndex(3, key));
				case 'E':
					recipe.setIngredient('E', getMaterialAtIndex(4, key));
				case 'F':
					recipe.setIngredient('F', getMaterialAtIndex(5, key));
				case 'G':
					recipe.setIngredient('G', getMaterialAtIndex(6, key));
				case 'H':
					recipe.setIngredient('H', getMaterialAtIndex(7, key));
				case 'I':
					recipe.setIngredient('I', getMaterialAtIndex(8, key));
				
				}
				
			}

			
			Bukkit.addRecipe(recipe);	
		}		

	}

	private String getString(String key, String path) {
		return ChatColor.translateAlternateColorCodes('&', types.getString("types." + key + "." + path));
	}

	private List<String> getStringList(String key, String path) {

		List<String> newList = new ArrayList<>();
		for (String s : types.getStringList("types." + key + "." + path)) {
			newList.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		return newList;
	}
	
	private Material getMaterialAtIndex(int index, String key) {
		
		List<String> t = getStringList(key, "recipe");
		String material = "";
		for (String s : t) { material += ":" + s; }
		material = material.substring(1);
		String[] matList = material.split(":");
		
		return Material.valueOf(matList[index]);
	}

}
