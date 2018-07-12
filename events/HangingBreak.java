package events;



import net.apherfox.someplugin.Pair;

import org.bukkit.event.EventHandler;

import org.bukkit.event.Listener;

import org.bukkit.event.hanging.HangingBreakEvent;



public class HangingBreak

  implements Listener

{

  @EventHandler

  public void onHangingBreak(HangingBreakEvent event)

  {

    for (Pair pair : Pair.pairs) {

      if ((pair.bat != null) && (event.getEntity().equals(pair.bat))) {

        event.setCancelled(true);

      }

    }

  }

}