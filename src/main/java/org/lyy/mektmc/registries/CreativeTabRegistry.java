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
                    .displayItems((parameters, output) ->
                          ItemRegistry.ITEMS.getEntries().forEach(item -> output.accept(item.get())))
                    .build());

    private CreativeTabRegistry() {
    }
}
