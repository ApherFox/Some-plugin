package GUIs;



import java.util.ArrayList;

import java.util.Arrays;

import java.util.Date;

import main.Message;

import main.Pair;

import org.bukkit.Bukkit;

import org.bukkit.Material;

import org.bukkit.OfflinePlayer;

import org.bukkit.Sound;

import org.bukkit.entity.Player;

import org.bukkit.event.inventory.InventoryClickEvent;

import org.bukkit.event.inventory.InventoryCloseEvent;

import org.bukkit.inventory.Inventory;



public class Request

  extends Leash

{

  private Pair pair;

  

  public Request(Player player, Pair pair)

  {

    super(player);

    

    this.pair = pair;

  }

  

  public void open()

  {

    this.player.playSound(this.player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);

    

    this.inventory = Bukkit.createInventory(null, 9, "Leash request");

    

    super.open();

  }

  

  public void render()

  {

    this.inventory.setItem(0, item(Material.WOOL, 1, (short)5, "§aAccept", Arrays.asList(new String[0])));

    this.inventory.setItem(3, playerHead(Bukkit.getOfflinePlayer(this.pair.dominantID), true));

    this.inventory.setItem(4, edit(mode(this.pair.mode), "§fMode: ", Arrays.asList(new String[0])));

    this.inventory.setItem(5, playerHead(Bukkit.getOfflinePlayer(this.pair.submissiveID), false));

    this.inventory.setItem(8, item(Material.WOOL, 1, (short)14, "§cDeny", Arrays.asList(new String[0])));

  }

  

  public void onInventoryClick(InventoryClickEvent event)

  {

    event.setCancelled(true);

    if ((event.getRawSlot() == 0) || (event.getRawSlot() == 8))

    {

      Player requester = null;

      Player requestee = null;

      if (this.pair.requesterDominant)

      {

        requester = Bukkit.getPlayer(this.pair.dominantID);

        requestee = Bukkit.getPlayer(this.pair.submissiveID);

      }

      else

      {

        requester = Bukkit.getPlayer(this.pair.submissiveID);

        requestee = Bukkit.getPlayer(this.pair.dominantID);

      }

      if (event.getRawSlot() == 0)

      {

        OfflinePlayer submissive = Bukkit.getOfflinePlayer(this.pair.submissiveID);

        if (Pair.find(submissive) == null)

        {

          this.pair.accepted = true;

          this.pair.time = new Date().getTime();

          this.pair.update();

          Pair.save();

          

          this.player.closeInventory();

          this.player.updateInventory();

          if (requester != null) {

            Message.send(requester, "request.accept.requester", Arrays.asList(new String[] { Bukkit.getOfflinePlayer(this.pair.requesterDominant ? this.pair.submissiveID : this.pair.dominantID).getName() }));

          }

          if (requestee != null) {

            Message.send(requestee, "request.accept.requestee", Arrays.asList(new String[] { Bukkit.getOfflinePlayer(this.pair.requesterDominant ? this.pair.dominantID : this.pair.submissiveID).getName() }));

          }

          if (this.pair.dominant != null)

          {

            Message.send(this.pair.dominant, "info.dominant", Arrays.asList(new String[] { Bukkit.getOfflinePlayer(this.pair.submissiveID).getName() }));

            this.pair.dominant.playSound(this.pair.dominant.getLocation(), Sound.ENTITY_LEASHKNOT_PLACE, 1.0F, 1.0F);

          }

          if (this.pair.submissive != null)

          {

            Message.send(this.pair.submissive, "info.submissive", Arrays.asList(new String[0]));

            this.pair.submissive.playSound(this.pair.submissive.getLocation(), Sound.ENTITY_LEASHKNOT_PLACE, 1.0F, 1.0F);

          }

        }

        else

        {

          Message.send(this.player, "errors.alreadyleashed", Arrays.asList(new String[] { submissive.getName() }));

        }

      }

      else if (event.getRawSlot() == 8)

      {

        Pair.pairs.remove(this.pair);

        if (requester != null) {

          Message.send(requester, "request.deny.requester", Arrays.asList(new String[] { Bukkit.getOfflinePlayer(this.pair.requesterDominant ? this.pair.submissiveID : this.pair.dominantID).getName() }));

        }

        if (requestee != null) {

          Message.send(requestee, "request.deny.requestee", Arrays.asList(new String[] { Bukkit.getOfflinePlayer(this.pair.requesterDominant ? this.pair.dominantID : this.pair.submissiveID).getName() }));

        }

      }

      Pair.save();

      

      this.player.closeInventory();

      this.player.updateInventory();

    }

  }

  

  public void onInventoryClose(InventoryCloseEvent event) {}

}