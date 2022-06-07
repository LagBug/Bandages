package me.lagbug.bandages.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {

	private ItemStack item;
	private ItemMeta meta;

	public ItemBuilder(Material material, int amount, byte id) {
		item = new ItemStack(material, amount, id);
		meta = item.getItemMeta();
	}

	public ItemBuilder setDisplayName(String name) {
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		return this;
	}

	public ItemBuilder setLore(List<String> lore) {
		List<String> loreR = new ArrayList<>();
		for (String s : lore) {
			loreR.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		meta.setLore(loreR);
		return this;
	}

	public ItemStack create() {
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.spigot().setUnbreakable(true);
		item.setItemMeta(meta);
		return item;
	}
}
