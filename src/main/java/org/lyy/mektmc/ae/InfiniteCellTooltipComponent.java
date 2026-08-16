package org.lyy.mektmc.ae;

import appeng.api.stacks.AEKey;
import java.util.List;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public record InfiniteCellTooltipComponent(List<AEKey> keys) implements TooltipComponent {

    public InfiniteCellTooltipComponent {
        keys = List.copyOf(keys);
    }
}
