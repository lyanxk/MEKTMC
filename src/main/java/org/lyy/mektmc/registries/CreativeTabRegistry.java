package org.lyy.mektmc.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.lyy.mektmc.Mektmc;

public final class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Mektmc.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MEKTMC_TAB =
            CREATIVE_MODE_TABS.register("mektmc", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.mektmc"))
                    .icon(() -> ItemRegistry.INFINITE_ITEM_CONTAINER.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ItemRegistry.INFINITE_ITEM_CONTAINER.get());
                        output.accept(ItemRegistry.INFINITE_FLUID_CONTAINER.get());
                        output.accept(ItemRegistry.INFINITE_CHEMICAL_CONTAINER.get());
                        output.accept(ItemRegistry.INFINITE_ETHYLENE_CELL.get());
                        output.accept(ItemRegistry.INFINITE_DEUTERIUM_CELL.get());
                        output.accept(ItemRegistry.INFINITE_TRITIUM_CELL.get());
                        output.accept(ItemRegistry.INFINITE_D_T_FUEL_CELL.get());
                        output.accept(ItemRegistry.INFINITE_BRINE_CELL.get());
                        output.accept(ItemRegistry.INFINITE_LITHIUM_CELL.get());
                        output.accept(ItemRegistry.INFINITE_FISSILE_FUEL_CELL.get());
                    })
                    .build());

    private CreativeTabRegistry() {
    }
}
