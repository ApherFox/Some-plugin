package events;



import java.util.UUID;

import net.apherfox.someplugin.Pair;

import org.bukkit.entity.Bat;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;

import org.bukkit.event.Listener;

import org.bukkit.event.entity.PlayerDeathEvent;



public class PlayerDeath

  implements Listener

{

  @EventHandler

  public void onPlayerDeath(PlayerDeathEvent event)

  {

    Player player = event.getEntity();

    for (Pair pair : Pair.pairs) {

      if (pair.online) {

        if ((pair.dominantID.equals(player.getUniqueId())) || (pair.submissiveID.equals(player.getUniqueId()))) {

          pair.bat.remove();

        }

      }

    }

  }

}