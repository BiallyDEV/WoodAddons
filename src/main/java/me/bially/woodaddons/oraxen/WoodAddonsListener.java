package me.bially.woodaddons.oraxen;

import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.mechanics.provided.gameplay.noteblock.NoteBlockMechanicFactory;
import io.th0rgal.oraxen.shaded.customblockdata.CustomBlockData;
import me.bially.woodaddons.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.th0rgal.oraxen.api.OraxenBlocks.getNoteBlockMechanic;


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
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        ItemStack tap = OraxenItems.getItemById("tap").build();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.BIRCH_LOG) {
            if (player.getInventory().getItemInMainHand() == tap) {
                NoteBlockMechanicFactory.setBlockModel(block, "birch_log_tap");
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
        if (!Objects.equals(getNoteBlockMechanic(block).getItemID(), "birch_log_tap") || blockCooldownList.contains(block))
            return;

        item.subtract(1);
        player.getInventory().addItem(OraxenItems.getItemById("birch_sap").build());
        NoteBlockMechanicFactory.setBlockModel(block, "birch_log_tap_cooldown");
        PersistentDataContainer pdc = new CustomBlockData(block, plugin);
        blockCooldownList.add(block);
        if (pdc.has(usesKey, PersistentDataType.INTEGER)) {
            pdc.set(usesKey, PersistentDataType.INTEGER, pdc.get(usesKey, PersistentDataType.INTEGER) + 1);
        } else {
            pdc.set(usesKey, PersistentDataType.INTEGER, 1);
        }

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            NoteBlockMechanicFactory.setBlockModel(block, "birch_log_tap");
            blockCooldownList.remove(block);
            if (pdc.get(usesKey, PersistentDataType.INTEGER) >= 3) {
                block.setType(Material.STRIPPED_BIRCH_LOG, false);
                pdc.remove(usesKey);
            }
        }, 200L);
    }

    @EventHandler
    public void onBlockBreak(final BlockBreakEvent event) {
        blockCooldownList.remove(event.getBlock());
    }
}
