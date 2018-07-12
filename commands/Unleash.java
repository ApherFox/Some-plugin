package commands;



import java.util.ArrayList;

import java.util.Arrays;

import java.util.Date;

import main.Message;

import main.Pair;

import main.Plugin;

import org.bukkit.Bukkit;

import org.bukkit.OfflinePlayer;

import org.bukkit.Server;

import org.bukkit.Sound;

import org.bukkit.command.Command;

import org.bukkit.command.CommandExecutor;

import org.bukkit.command.CommandSender;

import org.bukkit.entity.Bat;

import org.bukkit.entity.Player;

import org.bukkit.plugin.java.JavaPlugin;



public class Unleash

  implements CommandExecutor

{

  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)

  {

    if ((sender instanceof Player))

    {

      Player player = (Player)sender;

      Pair pair = null;

      if (args.length < 1)

      {

        pair = Pair.find(player);

      }

      else

      {

        Player submissive = Plugin.plugin.getServer().getPlayer(args[0]);

        if (submissive != null)

        {

          pair = Pair.find(player, submissive);

        }

        else

        {

          Message.send(player, "errors.notfound", Arrays.asList(new String[] { args[0] }));

          return true;

        }

      }

      if ((pair != null) && (pair.accepted))

      {

        if (pair.submissive.equals(player)) {

          if (pair.mode != "softcore")

          {

            long now = new Date().getTime();

            int difference = 0;

            String str;

            switch ((str = pair.mode).hashCode())

            {

            case 49: 

              if (str.equals("1")) {}

              break;

            case 51: 

              if (str.equals("3")) {}

              break;

            case 54: 

              if (str.equals("6")) {}

              break;

            case 1572: 

              if (str.equals("15")) {

                break;

              }

              break;

            case 1629: 

              if (!str.equals("30"))

              {

                break label301;

                difference = 900000;

              }

              else

              {

                difference = 900000;

                break label301;

                difference = 3600000;

                break label301;

                difference = 10800000;

                break label301;

                difference = 21600000;

              }

              break;

            }

            label301:

            if (pair.mode == "hardcore")

            {

              Message.send(player, "errors.hardcore", Arrays.asList(new String[0]));

              return true;

            }

            if (now - pair.time < difference)

            {

              Message.send(player, "errors.time", Arrays.asList(new String[] { Integer.toString((int)Math.ceil((difference - (now - pair.time)) / 1000L / 60L)) }));

              return true;

            }

          }

        }

        if (pair.bat != null) {

          pair.bat.remove();

        }

        Pair.pairs.remove(pair);

        Pair.save();

        if (pair.dominant != null)

        {

          Message.send(pair.dominant, "unleash.dominant", Arrays.asList(new String[] { Bukkit.getOfflinePlayer(pair.submissiveID).getName() }));

          pair.dominant.playSound(pair.dominant.getLocation(), Sound.ENTITY_LEASHKNOT_BREAK, 1.0F, 1.0F);

        }

        if (pair.submissive != null)

        {

          Message.send(pair.submissive, "unleash.submissive", Arrays.asList(new String[0]));

          pair.submissive.playSound(pair.submissive.getLocation(), Sound.ENTITY_LEASHKNOT_BREAK, 1.0F, 1.0F);

        }

      }

      else

      {

        Message.send(player, "errors.notleashed", Arrays.asList(new String[0]));

      }

      return true;

    }

    return false;

  }

}