package me.bially.woodaddons;

import io.th0rgal.oraxen.mechanics.MechanicsManager;
import me.bially.woodaddons.barkaddon.BarkAddonListener;
import me.bially.woodaddons.oraxen.WoodAddonsListener;
import me.bially.woodaddons.oraxen.WoodAddonsMechanicFactory;
import me.bially.woodaddons.resinaddon.ResinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        {
            Bukkit.getPluginManager().registerEvents(new BarkAddonListener(this), this);
            Bukkit.getPluginManager().registerEvents(new ResinListener(this), this);
            MechanicsManager.registerMechanicFactory("woodaddons", WoodAddonsMechanicFactory::new);
        }
        this.getLogger().info("WoodAddons-v1.0-1.19 enabled.");
    }

    @Override
    public void onDisable() {this.getLogger().info("WoodAddons-v1.0-1.19 disabled.");
    }
}
