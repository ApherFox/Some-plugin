package GUIs;



import java.util.ArrayList;

import java.util.Arrays;

import java.util.Date;

import java.util.UUID;

import main.Message;

import main.Pair;

import main.Plugin;

import net.minecraft.server.v1_12_R1.EntityPlayer;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;

import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;

import net.minecraft.server.v1_12_R1.PacketPlayOutChat;

import net.minecraft.server.v1_12_R1.PlayerConnection;

import org.bukkit.Bukkit;

import org.bukkit.Material;

import org.bukkit.OfflinePlayer;

import org.bukkit.Sound;

import org.bukkit.configuration.file.YamlConfiguration;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;

import org.bukkit.entity.Player;

import org.bukkit.event.inventory.InventoryClickEvent;

import org.bukkit.event.inventory.InventoryCloseEvent;

import org.bukkit.inventory.Inventory;



public class Send

  extends Leash

{

  public Player dominant;

  public Player submissive;

  public String mode;

  

  public Send(Player player, Player dominant, Player submissive, String mode)

  {

    super(player);

    

    this.dominant = dominant;

    this.submissive = submissive;

    this.mode = mode;

  }

  

  public void render()

  {

    this.inventory.setItem(0, item(Material.WOOL, 1, (short)14, "§cCancel", Arrays.asList(new String[0])));

    this.inventory.setItem(3, edit(playerHead(this.dominant, true), "", Arrays.asList(new String[] { "§fClick to swap roles" })));

    this.inventory.setItem(4, edit(super.mode(this.mode), "§fMode: ", Arrays.asList(new String[] { "", "§fClick to change mode" })));

    this.inventory.setItem(5, edit(playerHead(this.submissive, false), "", Arrays.asList(new String[] { "§fClick to swap roles" })));

    this.inventory.setItem(8, item(Material.WOOL, 1, (short)11, "§9Send", this.player.hasPermission("playerleash.forceleash") ? Arrays.asList(new String[] { "§fClick to send leash request", "§fShift + Click to force leash" }) : Arrays.asList(new String[0])));

  }

  

  public void open()

  {

    this.inventory = Bukkit.createInventory(null, 9, "Send leash request");

    

    super.open();

  }

  

  public void onInventoryClick(InventoryClickEvent event)

  {

    event.setCancelled(true);

    switch (event.getRawSlot())

    {

    case 8: 

      send((this.player.hasPermission("playerleash.forceleash")) && (event.isShiftClick()));

      this.player.closeInventory();

      this.player.updateInventory();

      break;

    case 0: 

      this.player.closeInventory();

      this.player.updateInventory();

      break;

    case 3: 

    case 5: 

      Player lastDominant = this.dominant;

      this.dominant = this.submissive;

      this.submissive = lastDominant;

      render();

      break;

    case 4: 

      Mode mode = new Mode(this.player, this);

      mode.open();

    }

  }

  

  public void onInventoryClose(InventoryCloseEvent event) {}

  

  private void send(boolean force)

  {

    Pair pair = new Pair();

    Player other = null;

    boolean requesterDominant = false;

    if (this.player.equals(this.dominant))

    {

      other = this.submissive;

      requesterDominant = true;

    }

    else

    {

      other = this.dominant;

      requesterDominant = false;

    }

    pair.id = UUID.randomUUID();

    pair.dominantID = this.dominant.getUniqueId();

    pair.submissiveID = this.submissive.getUniqueId();

    pair.mode = this.mode;

    pair.time = new Date().getTime();

    pair.requesterDominant = requesterDominant;

    pair.accepted = force;

    

    pair.update();

    Pair.pairs.add(pair);

    Pair.save();

    if (!force)

    {

      String timeout = Integer.toString((int)Math.ceil(Plugin.config.getInt("timeout") / 1000 / 60));

      

      Message.send(this.player, "request.send.requester", Arrays.asList(new String[] { other.getName(), timeout }));

      if (other.isOnline())

      {

        ArrayList<String> messages = null;

        if (requesterDominant) {

          messages = Message.get("request.send.requestee.submissive", Arrays.asList(new String[] { Bukkit.getOfflinePlayer(pair.dominantID).getName(), timeout }));

        } else {

          messages = Message.get("request.send.requestee.dominant", Arrays.asList(new String[] { Bukkit.getOfflinePlayer(pair.submissiveID).getName(), timeout }));

        }

        for (int i = 0; i < messages.size(); i++)

        {

          String message = (String)messages.get(i);

          if (i == 0)

          {

            IChatBaseComponent component = IChatBaseComponent.ChatSerializer.a("{ \"text\":\"" + message + " \", \"extra\": [ { \"text\": \"" + (String)Message.get("button.text", Arrays.asList(new String[0])).get(0) + "\", \"hoverEvent\": { \"action\": \"show_text\", \"value\": \"" + (String)Message.get("button.hover", Arrays.asList(new String[0])).get(0) + "\" }, \"clickEvent\": { \"action\": \"run_command\", \"value\": \"/leash " + pair.id.toString() + "\" } } ] }");

            PacketPlayOutChat packet = new PacketPlayOutChat(component);

            ((CraftPlayer)other).getHandle().playerConnection.sendPacket(packet);

          }

          else

          {

            other.sendMessage(message);

          }

        }

        other.playSound(other.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);

      }

    }

  }

}