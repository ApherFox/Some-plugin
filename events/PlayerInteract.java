package events;



import net.apherfox.someplugin.Pair;

import org.bukkit.event.EventHandler;

import org.bukkit.event.Listener;

import org.bukkit.event.block.Action;

import org.bukkit.event.player.PlayerInteractEvent;



public class PlayerInteract

  implements Listener

{

  @EventHandler

  public void onPlayerInteract(PlayerInteractEvent event)

  {

    if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {

      switch (event.getClickedBlock().getType())

      {

      case COMMAND_CHAIN: 

      case GOLD_PLATE: 

      case GOLD_RECORD: 

      case GOLD_SWORD: 

      case GRASS: 

        for (Pair pair : Pair.pairs) {

          if ((pair.accepted) && (pair.dominant != null) && (event.getPlayer().equals(pair.dominant))) {

            event.setCancelled(true);

          }

        }

      }

    }

  }

}