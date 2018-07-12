package events;



import java.util.ArrayList;

import net.apherfox.someplugin.GUI;

import org.bukkit.event.EventHandler;

import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;



public class InventoryClick

  implements Listener

{

  @EventHandler

  public void onInventoryClick(InventoryClickEvent event)

  {

    ArrayList<GUI> later = new ArrayList();

    for (GUI gui : GUI.guis) {

      if (gui.inventory.equals(event.getInventory())) {

        later.add(gui);

      }

    }

    for (GUI gui : later) {

      gui.onInventoryClick(event);

    }

  }

}