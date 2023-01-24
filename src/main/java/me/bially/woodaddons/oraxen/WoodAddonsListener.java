package me.bially.woodaddons.oraxen;

import io.th0rgal.oraxen.api.OraxenBlocks;
import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.shaded.customblockdata.CustomBlockData;
import me.bially.woodaddons.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class WoodAddonsListener implements Listener {

    public JavaPlugin plugin = Main.getPlugin(Main.class);
    private final WoodAddonsMechanicFactory factory;
    private final List<Block> blockCooldownList = new ArrayList<>();
    public NamespacedKey usesKey = new NamespacedKey(plugin, "bottleUses");

    public WoodAddonsListener(WoodAddonsMechanicFactory factory) {
        super();
        this.factory = factory;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void BirchSapAddon1(final PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        String heldID = OraxenItems.getIdByItem(player.getInventory().getItemInMainHand());

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.BIRCH_LOG) {
            if (heldID.equals("tap")) {
                item.subtract(1);
                player.playSound(block.getLocation(), Sound.BLOCK_GLASS_BREAK, 100, 1.0F);
                OraxenBlocks.place("birch_log_tap_cooldown", block.getLocation());
                PersistentDataContainer pdc = new CustomBlockData(block, plugin);
                blockCooldownList.add(block);
                if (pdc.has(usesKey, PersistentDataType.INTEGER)) {
                    pdc.set(usesKey, PersistentDataType.INTEGER, pdc.get(usesKey, PersistentDataType.INTEGER) + 1);
                } else {
                    pdc.set(usesKey, PersistentDataType.INTEGER, 1);
                }


                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    OraxenBlocks.place("birch_log_tap", block.getLocation());
                    blockCooldownList.remove(block);
                }, 200L);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void BirchSapAddon2(final PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (event.getHand() != EquipmentSlot.HAND || event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (item == null || item.getType() != Material.GLASS_BOTTLE) return;
        if (block == null || block.getType() != Material.NOTE_BLOCK) return;
        if (!Objects.equals(OraxenBlocks.getNoteBlockMechanic(block).getItemID(), "birch_log_tap") || blockCooldownList.contains(block))
            return;

        item.subtract(1);
        player.playSound(block.getLocation(), Sound.ITEM_BOTTLE_FILL, 100, 1.0F);
        player.getInventory().addItem(OraxenItems.getItemById("birch_sap").build());
        OraxenBlocks.place("birch_log_tap_cooldown", block.getLocation());
        PersistentDataContainer pdc = new CustomBlockData(block, plugin);
        blockCooldownList.add(block);
        if (pdc.has(usesKey, PersistentDataType.INTEGER)) {
            pdc.set(usesKey, PersistentDataType.INTEGER, pdc.get(usesKey, PersistentDataType.INTEGER) + 1);
        } else {
            pdc.set(usesKey, PersistentDataType.INTEGER, 1);
        }

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            OraxenBlocks.place("birch_log_tap", block.getLocation());
            blockCooldownList.remove(block);
            if (pdc.get(usesKey, PersistentDataType.INTEGER) >= 4) {
                block.setType(Material.STRIPPED_BIRCH_LOG, false);
                player.playSound(block.getLocation(), Sound.BLOCK_GLASS_BREAK, 100, 1.0F);
                pdc.remove(usesKey);
            }
        }, 200L);
    }

    @EventHandler
    public void onBlockBreak(final BlockBreakEvent event) {
        blockCooldownList.remove(event.getBlock());
    }
}
