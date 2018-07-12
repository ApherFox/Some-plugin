package events;



import java.util.ArrayList;

import net.apherfox.someplugin.GUI;

import net.apherfox.someplugin.SomePlugin;

import org.bukkit.Server;

import org.bukkit.event.EventHandler;

import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryCloseEvent;

import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.scheduler.BukkitScheduler;



public class InventoryClose

  implements Listener

{

  @EventHandler

  public void onInventoryClose(final InventoryCloseEvent event)

  {

    Plugin.plugin.getServer().getScheduler().runTaskLater(Plugin.plugin, new Runnable()

    {

      public void run()

      {

        ArrayList<GUI> later = new ArrayList();

        for (GUI gui : GUI.guis) {

          if (gui.inventory.equals(event.getInventory())) {

            later.add(gui);

          }

        }

        for (GUI gui : later)

        {

          gui.onInventoryClose(event);

          GUI.guis.remove(gui);

        }

      }

    }, 1L);

  }

}