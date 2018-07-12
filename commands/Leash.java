package commands;



import GUIs.Request;

import java.util.Arrays;

import java.util.UUID;

import main.Message;

import main.Pair;

import org.bukkit.command.Command;

import org.bukkit.command.CommandExecutor;

import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;



public class Leash

  implements CommandExecutor

{

  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)

  {

    if ((sender instanceof Player))

    {

      Player player = (Player)sender;

      if (args.length > 0)

      {

        UUID id = UUID.fromString(args[0]);

        if (id != null) {

          for (Pair pair : Pair.pairs) {

            if (!pair.accepted)

            {

              UUID requesteeID = null;

              if (pair.requesterDominant) {

                requesteeID = pair.submissiveID;

              } else {

                requesteeID = pair.dominantID;

              }

              if ((pair.id.equals(id)) && (player.getUniqueId().equals(requesteeID)))

              {

                Request request = new Request(player, pair);

                request.open();

                

                return true;

              }

            }

          }

        }

        Message.send(player, "errors.invalid", Arrays.asList(new String[0]));

        

        return true;

      }

    }

    return false;

  }

}