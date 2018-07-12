package net.apherfox.someplugin;



import events.EntityDamage;

import events.HangingBreak;

import events.InventoryClick;

import events.InventoryClose;

import events.PlayerDeath;

import events.PlayerInteract;

import events.PlayerInteractEntity;

import events.PlayerJoin;

import events.PlayerMove;

import events.PlayerQuit;

import events.PlayerRespawn;

import org.bukkit.Bukkit;

import org.bukkit.Server;

import org.bukkit.plugin.PluginManager;



public class Events

{

  public static void register()

  {

    Bukkit.getServer().getPluginManager().registerEvents(new PlayerInteractEntity(), Plugin.plugin);

    Bukkit.getServer().getPluginManager().registerEvents(new PlayerMove(), Plugin.plugin);

    Bukkit.getServer().getPluginManager().registerEvents(new EntityDamage(), Plugin.plugin);

    Bukkit.getServer().getPluginManager().registerEvents(new PlayerRespawn(), Plugin.plugin);

    Bukkit.getServer().getPluginManager().registerEvents(new PlayerDeath(), Plugin.plugin);

    Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoin(), Plugin.plugin);

    Bukkit.getServer().getPluginManager().registerEvents(new PlayerQuit(), Plugin.plugin);

    Bukkit.getServer().getPluginManager().registerEvents(new HangingBreak(), Plugin.plugin);

    Bukkit.getServer().getPluginManager().registerEvents(new PlayerInteract(), Plugin.plugin);

    



    Bukkit.getServer().getPluginManager().registerEvents(new InventoryClick(), Plugin.plugin);

    Bukkit.getServer().getPluginManager().registerEvents(new InventoryClose(), Plugin.plugin);

  }

}