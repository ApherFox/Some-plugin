package net.apherfox.someplugin;



import java.io.File;

import java.io.IOException;

import java.text.MessageFormat;

import java.util.ArrayList;

import java.util.Arrays;

import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import org.bukkit.configuration.file.YamlConfigurationOptions;

import org.bukkit.entity.Player;

import org.bukkit.plugin.java.JavaPlugin;



public class Message

{

  private static File file = new File(Plugin.plugin.getDataFolder(), "messages.yml");

  private static YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

  

  public static void init()

  {

    config.addDefault("request.send.requester", Arrays.asList(new String[] { "&eLeash request sent to &6{0}&e!", "&fRequest will timeout in &7{1} &fminute" }));

    config.addDefault("request.send.requestee.dominant", Arrays.asList(new String[] { "&6{0} &ewould like you to leash them!", "&fRequest will timeout in &7{1} &fminute" }));

    config.addDefault("request.send.requestee.submissive", Arrays.asList(new String[] { "&6{0} &ewould like to leash you!", "&fRequest will timeout in &7{1} &fminute" }));

    config.addDefault("request.accept.requester", Arrays.asList(new String[] { "&2{0} &aaccepted your leash request!" }));

    config.addDefault("request.accept.requestee", Arrays.asList(new String[] { "&aAccepted leash request from &2{0}&a!" }));

    config.addDefault("request.deny.requester", Arrays.asList(new String[] { "&4{0} &cdenied your leash request!" }));

    config.addDefault("request.deny.requestee", Arrays.asList(new String[] { "&cDenied leash request from &4{0}&c!" }));

    config.addDefault("request.expire.requester", Arrays.asList(new String[] { "&cLeash request to &4{0} &cexpired!" }));

    config.addDefault("request.expire.requestee", Arrays.asList(new String[] { "&cLeash request from &4{0} &cexpired!" }));

    

    config.addDefault("info.dominant", Arrays.asList(new String[] { "&fUnleash &7{0} &fby executing &7/unleash {0}" }));

    config.addDefault("info.submissive", Arrays.asList(new String[] { "&fUnleash yourself by executing &7/unleash" }));

    

    config.addDefault("button.text", Arrays.asList(new String[] { "&f&o[Open request]" }));

    config.addDefault("button.hover", Arrays.asList(new String[] { "&fClick to open request" }));

    

    config.addDefault("unleash.dominant", Arrays.asList(new String[] { "&7{0} &funleashed!" }));

    config.addDefault("unleash.submissive", Arrays.asList(new String[] { "&fUnleashed!" }));

    

    config.addDefault("errors.alreadyleashed", Arrays.asList(new String[] { "&4{0} &cis already leashed!" }));

    config.addDefault("errors.invalid", Arrays.asList(new String[] { "&cInvalid request!" }));

    config.addDefault("errors.notfound", Arrays.asList(new String[] { "&cPlayer with name &4{0} &cnot found!" }));

    config.addDefault("errors.notleashed", Arrays.asList(new String[] { "&cNot leashed!" }));

    config.addDefault("errors.time", Arrays.asList(new String[] { "&cYou may unleash yourself in &4{0} &cminutes!" }));

    config.addDefault("errors.hardcore", Arrays.asList(new String[] { "&cYou not unleash yourself in hardcore mode!" }));

    if (!file.exists())

    {

      config.options().copyDefaults(true);

      try

      {

        config.save(file);

      }

      catch (IOException localIOException) {}

    }

  }

  

  public static ArrayList<String> get(String path, List<String> arguments)

  {

    ArrayList<String> messages = new ArrayList();

    for (String message : config.getStringList(path)) {

      messages.add(MessageFormat.format(message.replaceAll("'", "''").replaceAll("&", "§"), arguments.toArray()));

    }

    return messages;

  }

  

  public static void send(Player player, String path, List<String> arguments)

  {

    for (String message : get(path, arguments)) {

      player.sendMessage(message);

    }

  }

}