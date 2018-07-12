package events;



import java.util.UUID;

import net.apherfox.someplugin.Pair;

import net.apherfox.someplugin.SomePlugin;

import org.bukkit.Server;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;

import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerRespawnEvent;

import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.scheduler.BukkitScheduler;



public class PlayerRespawn

  implements Listener

{

  @EventHandler

  public void onPlayerRespawn(final PlayerRespawnEvent event)

  {

    Plugin.plugin.getServer().getScheduler().runTaskLater(Plugin.plugin, new Runnable()

    {

      public void run()

      {

        Player player = event.getPlayer();

        for (Pair pair : Pair.pairs) {

          if (pair.online) {

            if ((pair.dominantID.equals(player.getUniqueId())) || (pair.submissiveID.equals(player.getUniqueId())))

            {

              if (pair.submissiveID.equals(player.getUniqueId())) {

                event.setRespawnLocation(pair.dominant.getLocation());

              }

              pair.update();

            }

          }

        }

      }

    }, 1L);

  }

}