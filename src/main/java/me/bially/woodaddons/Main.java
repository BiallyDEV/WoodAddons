package me.bially.woodaddons;

import io.th0rgal.oraxen.mechanics.MechanicsManager;
import me.bially.woodaddons.barkaddon.BarkAddonListener;
import me.bially.woodaddons.oraxen.WoodAddonsMechanicFactory;
import me.bially.woodaddons.resinaddon.ResinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        enable();
    }

    @Override
    public void onDisable() {this.getLogger().info("WoodAddons-v1.0-1.19 disabled.");
    }

    private void enable() {
        if (Bukkit.getPluginManager().isPluginEnabled("Oraxen")) {
            Bukkit.getScheduler().runTaskLater(this, () -> {
                this.saveDefaultConfig();
                MechanicsManager.registerMechanicFactory("woodaddons", WoodAddonsMechanicFactory::new);
                Bukkit.getPluginManager().registerEvents(new BarkAddonListener(this), this);
                Bukkit.getPluginManager().registerEvents(new ResinListener(this), this);
                this.getLogger().info("WoodAddons-v1.0-1.19 enabled.");
            }, 20L);
        } else Bukkit.getScheduler().runTaskLater(this, this::enable, 20L);
    }
}
