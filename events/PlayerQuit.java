package events;



import java.util.UUID;

import java.util.logging.Logger;

import net.apherfox.someplugin.Pair;

import net.apherfox.someplugin.SomePlugin;

import org.bukkit.entity.Bat;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;

import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerQuitEvent;

import org.bukkit.plugin.java.JavaPlugin;



public class PlayerQuit

  implements Listener

{

  @EventHandler

  public void onPlayerQuit(PlayerQuitEvent event)

  {

    Player player = event.getPlayer();

    for (Pair pair : Pair.pairs) {

      if (pair.online) {

        if ((pair.dominantID.equals(player.getUniqueId())) || (pair.submissiveID.equals(player.getUniqueId())))

        {

          Plugin.plugin.getLogger().info("UPDATE");

          pair.bat.remove();

          pair.online = false;

        }

      }

    }

  }

}