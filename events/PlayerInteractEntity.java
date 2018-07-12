package events;



import GUIs.Send;

import java.util.Arrays;

import net.apherfox.someplugin.Message;

import net.apherfox.someplugin.Pair;

import org.bukkit.Material;

import org.bukkit.entity.Entity;

import org.bukkit.entity.EntityType;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;

import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerInteractEntityEvent;

import org.bukkit.inventory.EquipmentSlot;

import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.PlayerInventory;



public class PlayerInteractEntity

  implements Listener

{

  @EventHandler

  public void onPlayerInteractEntity(PlayerInteractEntityEvent event)

  {

    if (event.getRightClicked().getType() == EntityType.PLAYER)

    {

      Player player = event.getPlayer();

      Player clicked = (Player)event.getRightClicked();

      if ((event.getHand() == EquipmentSlot.HAND) && (player.getInventory().getItemInMainHand().getType() == Material.LEASH))

      {

        if (Pair.find(clicked) == null)

        {

          Send send = new Send(player, player, clicked, "softcore");

          send.open();

        }

        else

        {

          Message.send(player, "errors.alreadyleashed", Arrays.asList(new String[] { clicked.getName() }));

        }

        event.setCancelled(true);

      }

    }

  }

}