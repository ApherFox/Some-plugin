package GUIs;



import org.bukkit.Bukkit;

import org.bukkit.entity.Player;

import org.bukkit.event.inventory.InventoryClickEvent;

import org.bukkit.event.inventory.InventoryCloseEvent;

import org.bukkit.inventory.Inventory;



public class Mode

  extends Leash

{

  private Send send;

  private boolean ignore = false;

  

  public Mode(Player player, Send send)

  {

    super(player);

    

    this.send = send;

  }

  

  public void render()

  {

    this.inventory.setItem(0, mode("softcore"));

    this.inventory.setItem(2, mode("15"));

    this.inventory.setItem(3, mode("30"));

    this.inventory.setItem(4, mode("1"));

    this.inventory.setItem(5, mode("3"));

    this.inventory.setItem(6, mode("6"));

    this.inventory.setItem(8, mode("hardcore"));

  }

  

  public void open()

  {

    this.inventory = Bukkit.createInventory(null, 9, "Select mode");

    

    super.open();

  }

  

  public void onInventoryClick(InventoryClickEvent event)

  {

    event.setCancelled(true);

    

    String mode = null;

    switch (event.getRawSlot())

    {

    case 0: 

      mode = "softcore";

      break;

    case 2: 

      mode = "15";

      break;

    case 3: 

      mode = "30";

      break;

    case 4: 

      mode = "1";

      break;

    case 5: 

      mode = "3";

      break;

    case 6: 

      mode = "6";

      break;

    case 8: 

      mode = "hardcore";

    }

    if (mode != null)

    {

      this.ignore = true;

      

      Send send = new Send(this.player, this.send.dominant, this.send.submissive, mode);

      send.open();

    }

  }

  

  public void onInventoryClose(InventoryCloseEvent event)

  {

    if (!this.ignore)

    {

      this.ignore = true;

      

      Send send = new Send(this.player, this.send.dominant, this.send.submissive, this.send.mode);

      send.open();

    }

  }

}