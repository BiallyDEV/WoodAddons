package me.bially.woodaddons.oraxen;

import io.th0rgal.oraxen.mechanics.Mechanic;
import io.th0rgal.oraxen.mechanics.MechanicFactory;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.block.Action;

public class WoodAddonsMechanic extends Mechanic {

    private final String nextBlock;
    private final String requiredItem;
    private final Action onAction;

    public WoodAddonsMechanic(MechanicFactory factory, ConfigurationSection section) {
        super(factory, section);
        nextBlock = section.getString("nextBlock", null);
        requiredItem = section.getString("requiredItem", null);
        onAction = Action.valueOf(section.getString("onAction", "RIGHT_CLICK_BLOCK"));
    }

    public boolean hasNextBlock() { return nextBlock != null; }
    public String getNextBlock() { return nextBlock; }

    public boolean hasItemRequirement() { return requiredItem != null; }
    public String getRequiredItem() { return requiredItem; }

    public Action getOnAction() { return onAction; }
}
