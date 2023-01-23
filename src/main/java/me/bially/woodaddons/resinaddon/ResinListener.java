package me.bially.woodaddons.resinaddon;

import com.destroystokyo.paper.MaterialSetTag;
import io.th0rgal.oraxen.api.OraxenItems;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public class ResinListener implements Listener {
    private final Plugin plugin;

    public ResinListener(@NotNull Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void ResinAddon(final PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        ItemStack resin = OraxenItems.getItemById("resin").build();

        FileConfiguration config = plugin.getConfig();

        int chance = ThreadLocalRandom.current().nextInt(100) + 1; // random int from 1 to 100

        if (player.getInventory().getItemInMainHand().getType().toString().contains("AXE")) {
            if (block != null && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (MaterialSetTag.LOGS.isTagged(block.getType())) {
                    if (chance <= config.getInt("resin_drop")) { // 12% chance
                        player.getWorld().dropItemNaturally(block.getRelative(player.getFacing().getOppositeFace()).getLocation(), resin);
                    }
                }
            }
        }
    }
}
