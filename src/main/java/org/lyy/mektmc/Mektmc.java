package org.lyy.mektmc;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import org.lyy.mektmc.blocks.InfiniteItemContainerInteractions;
import org.lyy.mektmc.ini.Setup;
import org.lyy.mektmc.registries.BlockEntityRegistry;
import org.lyy.mektmc.registries.BlockRegistry;
import org.lyy.mektmc.registries.CreativeTabRegistry;
import org.lyy.mektmc.registries.ItemRegistry;

@Mod(Mektmc.MODID)
public final class Mektmc {

    public static final String MODID = "mektmc";

    public Mektmc(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        BlockRegistry.BLOCKS.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
        BlockEntityRegistry.BLOCK_ENTITY_TYPES.register(modEventBus);
        CreativeTabRegistry.CREATIVE_MODE_TABS.register(modEventBus);
        modEventBus.addListener(BlockEntityRegistry::registerCapabilities);
        modEventBus.addListener(Setup::onCommonSetup);
        NeoForge.EVENT_BUS.addListener(InfiniteItemContainerInteractions::onRightClickBlock);
        NeoForge.EVENT_BUS.addListener(InfiniteItemContainerInteractions::onLeftClickBlock);
        NeoForge.EVENT_BUS.addListener(InfiniteItemContainerInteractions::onPlayerLoggedOut);
    }
}
