package org.lyy.mektmc.ini;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.lyy.mektmc.registries.ItemRegistry;

public final class ModCreativeTabAdditions {
    private ModCreativeTabAdditions() {}

    public static void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
        var tabKey = event.getTabKey();
        if (tabKey == null) return;

        if (!"appmek".equals(tabKey.location().getNamespace())) return;

        event.accept(ItemRegistry.CATEGORY_INDEX.get());
        event.accept(ItemRegistry.CATEGORIZED_TERMINAL.get());
        event.accept(ItemRegistry.CATEGORY_DISK.get());
        event.accept(ItemRegistry.INFINITE_ETHYLENE_CELL.get());
        event.accept(ItemRegistry.INFINITE_DEUTERIUM_CELL.get());
        event.accept(ItemRegistry.INFINITE_TRITIUM_CELL.get());
        event.accept(ItemRegistry.INFINITE_D_T_FUEL_CELL.get());
        event.accept(ItemRegistry.INFINITE_BRINE_CELL.get());
        event.accept(ItemRegistry.INFINITE_LITHIUM_CELL.get());
        event.accept(ItemRegistry.INFINITE_FISSILE_FUEL_CELL.get());
    }
}
