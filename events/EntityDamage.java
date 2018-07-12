package events;



import net.apherfox.someplugin.Pair;

import org.bukkit.entity.Entity;

import org.bukkit.entity.EntityType;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;

import org.bukkit.event.Listener;

import org.bukkit.event.entity.EntityDamageEvent;

import org.bukkit.event.entity.EntityDamageEvent.DamageCause;



public class EntityDamage

  implements Listener

{

  @EventHandler

  public void onEntityDamage(EntityDamageEvent event)

  {

    for (Pair pair : Pair.pairs) {

      if (pair.online)

      {

        Entity entity = event.getEntity();

        if (entity.getType() == EntityType.PLAYER) {

          if ((event.getCause() == EntityDamageEvent.DamageCause.FALL) && (pair.submissive.equals((Player)entity))) {

            event.setCancelled(true);

          }

        }

      }

    }

  }

}