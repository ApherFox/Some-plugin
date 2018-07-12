package events;



import net.apherfox.someplugin.Pair;

import org.bukkit.Location;

import org.bukkit.entity.Bat;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;

import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerMoveEvent;

import org.bukkit.util.Vector;



public class PlayerMove

  implements Listener

{

  @EventHandler

  public void onPlayerMove(PlayerMoveEvent event)

  {

    for (Pair pair : Pair.pairs) {

      if (pair.online)

      {

        Player player = event.getPlayer();

        if (pair.dominant.equals(player))

        {

          if (!event.getFrom().getWorld().equals(player.getLocation().getWorld())) {

            pair.update();

          }

        }

        else if (pair.submissive.equals(player)) {

          if (event.getFrom().getWorld().equals(player.getLocation().getWorld())) {

            pair.bat.teleport(player.getLocation().add(new Vector(0.0D, 0.1D, 0.0D)));

          } else {

            pair.move();

          }

        }

      }

    }

  }

}