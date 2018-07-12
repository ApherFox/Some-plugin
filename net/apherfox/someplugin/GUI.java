package net.apherfox.someplugin;



import java.util.ArrayList;

import java.util.List;

import org.bukkit.Material;

import org.bukkit.entity.Player;

import org.bukkit.event.inventory.InventoryClickEvent;

import org.bukkit.event.inventory.InventoryCloseEvent;

import org.bukkit.inventory.Inventory;

import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;



public abstract class GUI

{

  public static ArrayList<GUI> guis = new ArrayList();

  public Player player;

  public Inventory inventory;

  

  public GUI(Player player)

  {

    this.player = player;

    

    guis.add(this);

  }

  

  protected ItemStack item(Material material, int count, short damage, String name, List<String> lore)

  {

    ItemStack item = new ItemStack(material, count, damage);

    

    ItemMeta meta = item.getItemMeta();

    meta.setDisplayName(name);

    meta.setLore(lore);

    

    item.setItemMeta(meta);

    

    return item;

  }

  

  public void open()

  {

    this.player.openInventory(this.inventory);

    

    render();

  }

  

  public abstract void render();

  

  public abstract void onInventoryClick(InventoryClickEvent paramInventoryClickEvent);

  

  public abstract void onInventoryClose(InventoryCloseEvent paramInventoryCloseEvent);

}