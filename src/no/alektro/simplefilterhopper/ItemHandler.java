package no.alektro.simplefilterhopper;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;

public class ItemHandler implements Listener {
	public ItemHandler() {
		
	}
	
	@EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent event) {
		Location location = event.getDestination().getLocation();
		World world = location.getWorld();
		Block block = world.getBlockAt(location);

		if (!block.getType().equals(Material.HOPPER)) {
			return;
		}
		
		Boolean foundItemFilter = false;

		for (Entity entity : world.getNearbyEntities(block.getLocation(), 2, 2, 2)) {
            if (entity instanceof ItemFrame && entity.getLocation().getBlock().getRelative(((ItemFrame) entity).getAttachedFace()).equals(block)) {
            	ItemFrame itemFrame = (ItemFrame) entity;
            	foundItemFilter = true;
            	
            	if (event.getItem().isSimilar(itemFrame.getItem())) {
            		return; // Allow item through
            	}
            }
        }
		
		if (foundItemFilter) {
			event.setCancelled(true);
		}
    }
	
	@EventHandler
    public void onInventoryPickupItem(InventoryPickupItemEvent event) {
		Location location = event.getInventory().getLocation();
		World world = location.getWorld();
		Block block = world.getBlockAt(location);
		
		if (!block.getType().equals(Material.HOPPER)) {
			return;
		}
		
		Boolean foundItemFilter = false;
		for (Entity entity : world.getNearbyEntities(block.getLocation(), 2, 2, 2)) {
            if (entity instanceof ItemFrame && entity.getLocation().getBlock().getRelative(((ItemFrame) entity).getAttachedFace()).equals(block)) {
            	ItemFrame itemFrame = (ItemFrame) entity;
            	foundItemFilter = true;
            	
            	if (event.getItem().getItemStack().isSimilar(itemFrame.getItem())) {
            		return; // Allow item through
            	}
            }
        }
		
		if (foundItemFilter) {
			event.setCancelled(true);
		}
    }
}
