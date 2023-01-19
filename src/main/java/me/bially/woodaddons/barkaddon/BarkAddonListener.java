package me.bially.woodaddons.barkaddon;

import io.th0rgal.oraxen.api.OraxenItems;
import org.bukkit.Material;
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

public class BarkAddonListener implements Listener {
    private final Plugin plugin;

    public BarkAddonListener(@NotNull Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void BarkAddon(final PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        ItemStack oak_bark = OraxenItems.getItemById("oak_bark").build();
        ItemStack spruce_bark = OraxenItems.getItemById("spruce_bark").build();
        ItemStack birch_bark = OraxenItems.getItemById("birch_bark").build();
        ItemStack jungle_bark = OraxenItems.getItemById("jungle_bark").build();
        ItemStack acacia_bark = OraxenItems.getItemById("acacia_bark").build();
        ItemStack dark_oak_bark = OraxenItems.getItemById("dark_oak_bark").build();
        ItemStack mangrove_bark = OraxenItems.getItemById("mangrove_bark").build();
        ItemStack crimson_bark = OraxenItems.getItemById("crimson_bark").build();
        ItemStack warped_bark = OraxenItems.getItemById("warped_bark").build();

        FileConfiguration config = plugin.getConfig();

        oak_bark.setAmount(config.getInt("bark_count"));
        spruce_bark.setAmount(config.getInt("bark_count"));
        birch_bark.setAmount(config.getInt("bark_count"));
        jungle_bark.setAmount(config.getInt("bark_count"));
        acacia_bark.setAmount(config.getInt("bark_count"));
        dark_oak_bark.setAmount(config.getInt("bark_count"));
        mangrove_bark.setAmount(config.getInt("bark_count"));
        crimson_bark.setAmount(config.getInt("bark_count"));
        warped_bark.setAmount(config.getInt("bark_count"));

        if (player.getInventory().getItemInMainHand().getType().toString().contains("AXE")) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (block.getType() == Material.OAK_LOG || block.getType() == Material.OAK_WOOD) {
                    player.getWorld().dropItemNaturally(block.getRelative(player.getFacing().getOppositeFace()).getLocation(), oak_bark);
                }
                else if (block.getType() == Material.SPRUCE_LOG || block.getType() == Material.SPRUCE_WOOD) {
                    player.getWorld().dropItemNaturally(block.getRelative(player.getFacing().getOppositeFace()).getLocation(), spruce_bark);
                }
                else if (block.getType() == Material.BIRCH_LOG || block.getType() == Material.BIRCH_WOOD) {
                    player.getWorld().dropItemNaturally(block.getRelative(player.getFacing().getOppositeFace()).getLocation(), birch_bark);
                }
                else if (block.getType() == Material.JUNGLE_LOG || block.getType() == Material.JUNGLE_WOOD) {
                    player.getWorld().dropItemNaturally(block.getRelative(player.getFacing().getOppositeFace()).getLocation(), jungle_bark);
                }
                else if (block.getType() == Material.ACACIA_LOG || block.getType() == Material.ACACIA_WOOD) {
                    player.getWorld().dropItemNaturally(block.getRelative(player.getFacing().getOppositeFace()).getLocation(), acacia_bark);
                }
                else if (block.getType() == Material.DARK_OAK_LOG || block.getType() == Material.DARK_OAK_WOOD) {
                    player.getWorld().dropItemNaturally(block.getRelative(player.getFacing().getOppositeFace()).getLocation(), dark_oak_bark);
                }
                else if (block.getType() == Material.MANGROVE_LOG || block.getType() == Material.MANGROVE_WOOD) {
                    player.getWorld().dropItemNaturally(block.getRelative(player.getFacing().getOppositeFace()).getLocation(), mangrove_bark);
                }
                if (plugin.getConfig().getString("nether_type").equals("true")) {
                    if (block.getType() == Material.CRIMSON_STEM || block.getType() == Material.CRIMSON_HYPHAE) {
                        player.getWorld().dropItemNaturally(block.getRelative(player.getFacing().getOppositeFace()).getLocation(), crimson_bark);
                    }
                    else if (block.getType() == Material.WARPED_STEM || block.getType() == Material.WARPED_HYPHAE) {
                        player.getWorld().dropItemNaturally(block.getRelative(player.getFacing().getOppositeFace()).getLocation(), warped_bark);
                    }
                }
            }
        }
    }
}
