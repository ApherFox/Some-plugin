package net.apherfox.someplugin;



import java.io.File;

import java.io.IOException;

import java.util.ArrayList;

import java.util.Arrays;

import java.util.Date;

import java.util.HashMap;

import java.util.Iterator;

import java.util.List;

import java.util.Map;

import java.util.UUID;

import org.bukkit.Bukkit;

import org.bukkit.Location;

import org.bukkit.OfflinePlayer;

import org.bukkit.Server;

import org.bukkit.World;

import org.bukkit.configuration.file.YamlConfiguration;

import org.bukkit.entity.Bat;

import org.bukkit.entity.Entity;

import org.bukkit.entity.EntityType;

import org.bukkit.entity.Player;

import org.bukkit.event.Listener;

import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.potion.PotionEffect;

import org.bukkit.potion.PotionEffectType;

import org.bukkit.scheduler.BukkitScheduler;

import org.bukkit.util.Vector;



public class Pair

  implements Listener

{

  public static ArrayList<Pair> pairs = new ArrayList();

  public UUID id;

  public UUID dominantID;

  public UUID submissiveID;

  public UUID batID;

  public Player dominant;

  public Player submissive;

  public Bat bat;

  public String mode;

  public long time;

  public boolean requesterDominant;

  public boolean accepted;

  public boolean online;

  private static File file = new File(Plugin.plugin.getDataFolder(), "data.yml");

  private static YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

  

  public static void init()

  {

     load();

    save();

    



    Bukkit.getScheduler().runTaskTimer(Plugin.plugin, new Runnable()

    {

      public void run()

      {

        for (Pair pair : Pair.pairs) {

          if (pair.online) {

            pair.move();

          }

        }

      }

    }, 5L, 5L);

    



    Bukkit.getScheduler().runTaskTimer(Plugin.plugin, new Runnable()    {

      public void run()

      {

        ArrayList<Pair> later = new ArrayList();

        long now = new Date().getTime();

        for (Pair pair : Pair.pairs) {

          if ((!pair.accepted) && (now - pair.time > Plugin.config.getInt("timeout")))

          {

            later.add(pair);

            

            Player requester = null;

            Player requestee = null;

            if (pair.requesterDominant)

            {

              requester = Bukkit.getPlayer(pair.dominantID);

              requestee = Bukkit.getPlayer(pair.submissiveID);

            }

            else

            {

              requester = Bukkit.getPlayer(pair.submissiveID);

              requestee = Bukkit.getPlayer(pair.dominantID);

            }

            if (requester != null) {

              Message.send(requester, "request.expire.requester", Arrays.asList(new String[] { Bukkit.getOfflinePlayer(pair.requesterDominant ? pair.submissiveID : pair.dominantID).getName() }));

            }

            if (requestee != null) {

              Message.send(requestee, "request.expire.requestee", Arrays.asList(new String[] { Bukkit.getOfflinePlayer(pair.requesterDominant ? pair.dominantID : pair.submissiveID).getName() }));

            }

          }

        }

        for (Pair pair : later) {

          Pair.pairs.remove(pair);

        }

        Pair.save();

      }

    }, 20L, 20L);

  }

  

  public static void load()

  {

    if (file.exists())

    {

      if (config.isList("pairs")) {

        for (Map<?, ?> map : config.getMapList("pairs"))

        {

          Pair pair = new Pair();

          

          pair.id = UUID.fromString((String)map.get("id"));

          pair.dominantID = UUID.fromString((String)map.get("dominant"));

          pair.submissiveID = UUID.fromString((String)map.get("submissive"));

          pair.batID = (map.get("bat") != null ? UUID.fromString((String)map.get("bat")) : null);

          pair.mode = ((String)map.get("mode"));

          pair.time = ((Long)map.get("time")).longValue();

          pair.requesterDominant = ((Boolean)map.get("requesterDominant")).booleanValue();

          pair.accepted = ((Boolean)map.get("accepted")).booleanValue();

          

          pairs.add(pair);

          pair.update();

        }

      }

    }

    else {

      try

      {

        config.save(file);

      }

      catch (IOException localIOException) {}

    }

  }

  

  public static void save()

  {

    List<Map<?, ?>> listMap = new ArrayList();

    for (Pair pair : pairs)

    {

      Map<String, Object> map = new HashMap();

      

      map.put("id", pair.id.toString());

      map.put("dominant", pair.dominantID.toString());

      map.put("submissive", pair.submissiveID.toString());

      map.put("bat", pair.batID != null ? pair.batID.toString() : null);

      map.put("mode", pair.mode);

      map.put("time", Long.valueOf(pair.time));

      map.put("requesterDominant", Boolean.valueOf(pair.requesterDominant));

      map.put("accepted", Boolean.valueOf(pair.accepted));

      

      listMap.add(map);

    }

    config.set("pairs", listMap);

    try

    {

      config.save(file);

    }

    catch (IOException localIOException) {}

  }

  

  public static Pair find(OfflinePlayer submissive)

  {

    for (Pair pair : pairs) {

      if ((pair.accepted) && (pair.submissiveID.equals(submissive.getUniqueId()))) {

        return pair;

      }

    }

    return null;

  }

  

  public static Pair find(OfflinePlayer dominant, OfflinePlayer submissive)

  {

    for (Pair pair : pairs) {

      if ((pair.accepted) && (pair.dominantID.equals(dominant.getUniqueId())) && (pair.submissiveID.equals(submissive.getUniqueId()))) {

        return pair;

      }

    }

    return null;

  }

  

  public void move()

  {

    if (this.dominant.getWorld().equals(this.submissive.getLocation().getWorld()))

    {

      double distance = this.dominant.getLocation().distance(this.submissive.getLocation());

      if (distance > 10.0D)

      {

        this.submissive.teleport(this.dominant);

      }

      else if (distance > 3.0D)

      {

        double x = this.dominant.getLocation().getX() - this.submissive.getLocation().getX();

        double y = this.dominant.getLocation().getY() - this.submissive.getLocation().getY();

        double z = this.dominant.getLocation().getZ() - this.submissive.getLocation().getZ();

        Vector vector = new Vector(x, y, z);

        

        this.submissive.setVelocity(vector.multiply(0.1D));

      }

    }

    else

    {

      this.submissive.teleport(this.dominant);

    }

  }

  

  public void update()

  {

    this.dominant = Bukkit.getPlayer(this.dominantID);

    this.submissive = Bukkit.getPlayer(this.submissiveID);

    if (this.batID != null)

    {

      Server server = Bukkit.getServer();

      Iterator localIterator2;

      for (Iterator localIterator1 = server.getWorlds().iterator(); localIterator1.hasNext(); localIterator2.hasNext())

      {

        World world = (World)localIterator1.next();

        

        localIterator2 = world.getEntities().iterator(); continue;Entity entity = (Entity)localIterator2.next();

        if (entity.getUniqueId().equals(this.batID)) {

          this.bat = ((Bat)entity);

        }

      }

      if (this.bat != null) {

        this.bat.remove();

      }

    }

    if ((this.dominant != null) && (this.submissive != null))

    {

      if (this.accepted)

      {

        move();

        

        this.bat = ((Bat)this.submissive.getWorld().spawnEntity(this.submissive.getLocation(), EntityType.BAT));

        this.bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2147483647, 1));

        this.bat.setAI(false);

        this.bat.setInvulnerable(true);

        this.bat.setCollidable(false);

        this.bat.setSilent(true);

        this.bat.setLeashHolder(this.dominant);

        



        this.online = true;

        this.batID = this.bat.getUniqueId();

        save();

      }

    }

    else {

      this.online = false;

    }  }

}