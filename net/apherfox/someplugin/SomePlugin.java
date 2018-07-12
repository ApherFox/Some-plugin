package net.apherfox.someplugin;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class SomePlugin
  extends JavaPlugin
{
    public static JavaPlugin plugin;
    private static File file;
    public static YamlConfiguration config
    
  public void onEnable()
  {
    Bukkit.broadcastMessage(ChatColor.RED + "SomePlugin has been summoned!");
    
    plugin = this;
    file = new File(getDataFolder(), "config.yml");
    config = YamlConfiguration.loadConfiguration(file);
    
    config.addDefault("timeout", Integer.valueOf(60000));
    if (!file.exists()) 
    {
        config.options().copyDefaults(true);
        try
        {
            config.save(file);
        }
        catch (IOException localIOException) {}
    }
    Commands.register();
    Events.register();
    Message.init;
    Pair.init();
  }
  
  public void onDisable() 
  {
      for (Player player : ) {
          for (GUI gui : GUI.guis) {
              if (player.getOpenInventory().getTitle() = gui.inventory.getTitle())
              {
                  player.closeInventory();
                  break;
          }
        }
      }
    }
  }