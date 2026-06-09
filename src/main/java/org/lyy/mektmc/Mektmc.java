package org.lyy.mektmc;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.api.distmarker.Dist;

import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import org.lyy.mektmc.ini.ModCreativeTabAdditions;
import org.lyy.mektmc.ini.Setup;
import org.lyy.mektmc.client.ClientSetup;
import org.lyy.mektmc.network.NetworkHandler;
import org.lyy.mektmc.parts.CategorizedTerminalPart;
import org.lyy.mektmc.registries.BlockEntityRegistry;
import org.lyy.mektmc.registries.BlockRegistry;
import org.lyy.mektmc.registries.ItemRegistry;
import org.lyy.mektmc.registries.MenuRegistry;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Mektmc.MODID)
public class Mektmc {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "mektmc";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Mektmc(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        appeng.api.parts.PartModels.registerModels(
                CategorizedTerminalPart.MODEL_OFF,
                CategorizedTerminalPart.MODEL_ON,
                ResourceLocation.fromNamespaceAndPath("ae2", "part/display_base"),
                ResourceLocation.fromNamespaceAndPath("ae2", "part/display_status_off"),
                ResourceLocation.fromNamespaceAndPath("ae2", "part/display_status_on"),
                ResourceLocation.fromNamespaceAndPath("ae2", "part/display_status_has_channel")
        );

        BlockRegistry.BLOCKS.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
        BlockEntityRegistry.BLOCK_ENTITY_TYPES.register(modEventBus);
        MenuRegistry.MENUS.register(modEventBus);
        modEventBus.addListener(ModCreativeTabAdditions::onBuildCreativeTabContents);
        modEventBus.addListener(Setup::onCommonSetup);
        modEventBus.addListener(NetworkHandler::register);
        modEventBus.addListener(BlockEntityRegistry::registerCapabilities);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(ClientSetup::registerScreens);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock) LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

}
