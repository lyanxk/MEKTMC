package org.lyy.mektmc;

import java.util.List;
import java.util.Objects;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.ChemicalStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.fluids.FluidStack;

/**
 * Configurable insertion whitelists for the three infinite containers.
 * Entries are either registry names ({@code namespace:path}) or tags ({@code #namespace:path}).
 */
@EventBusSubscriber(modid = Mektmc.MODID)
public final class Config {

    private static final List<String> DEFAULT_ITEM_WHITELIST = List.of(
          "#minecraft:dirt",
          "#minecraft:logs",
          "#c:stones",
          "#c:ores",
          "#c:concretes",
          "#c:sands"
    );
    private static final List<String> DEFAULT_FLUID_WHITELIST = List.of(
          "#c:water",
          "#c:lava",
          "#c:brine",
          "#c:lithium",
          "#c:ethene"
    );
    private static final List<String> DEFAULT_CHEMICAL_WHITELIST = List.of(
          "mekanism:lithium",
          "#mekanism:deuterium",
          "#mekanism:tritium",
          "#mekanism:fusion_fuel",
          "mekanism:ethene"
    );

    private static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_WHITELIST;
    private static final ModConfigSpec.ConfigValue<List<? extends String>> FLUID_WHITELIST;
    private static final ModConfigSpec.ConfigValue<List<? extends String>> CHEMICAL_WHITELIST;
    static final ModConfigSpec SPEC;

    private static volatile List<WhitelistEntry> itemWhitelist = parseEntries(DEFAULT_ITEM_WHITELIST);
    private static volatile List<WhitelistEntry> fluidWhitelist = parseEntries(DEFAULT_FLUID_WHITELIST);
    private static volatile List<WhitelistEntry> chemicalWhitelist = parseEntries(DEFAULT_CHEMICAL_WHITELIST);

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.comment("Infinite container insertion whitelists. Use namespace:path for an exact entry or #namespace:path for a tag.")
              .push("containerWhitelist");
        ITEM_WHITELIST = builder
              .comment("Items accepted by the item container. An empty list rejects every item.")
              .defineListAllowEmpty("items", DEFAULT_ITEM_WHITELIST, () -> "minecraft:stone", Config::validateEntry);
        FLUID_WHITELIST = builder
              .comment("Fluids accepted by the fluid container. An empty list rejects every fluid.")
              .defineListAllowEmpty("fluids", DEFAULT_FLUID_WHITELIST, () -> "minecraft:water", Config::validateEntry);
        CHEMICAL_WHITELIST = builder
              .comment("Mekanism chemicals accepted by the chemical container. An empty list rejects every chemical.")
              .defineListAllowEmpty("chemicals", DEFAULT_CHEMICAL_WHITELIST, () -> "mekanism:ethene", Config::validateEntry);
        builder.pop();
        SPEC = builder.build();
    }

    public static boolean isItemAllowed(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        ResourceLocation registryName = BuiltInRegistries.ITEM.getKey(stack.getItem());
        for (WhitelistEntry entry : itemWhitelist) {
            if (entry.tag()
                  ? stack.is(TagKey.create(Registries.ITEM, entry.id()))
                  : entry.id().equals(registryName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isFluidAllowed(FluidStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        ResourceLocation registryName = BuiltInRegistries.FLUID.getKey(stack.getFluid());
        for (WhitelistEntry entry : fluidWhitelist) {
            if (entry.tag()
                  ? stack.is(TagKey.create(Registries.FLUID, entry.id()))
                  : entry.id().equals(registryName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isChemicalAllowed(ChemicalStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        ResourceLocation registryName = stack.getChemicalHolder().unwrapKey()
              .map(ResourceKey::location)
              .orElse(null);
        for (WhitelistEntry entry : chemicalWhitelist) {
            if (entry.tag()
                  ? stack.is(TagKey.create(MekanismAPI.CHEMICAL_REGISTRY_NAME, entry.id()))
                  : entry.id().equals(registryName)) {
                return true;
            }
        }
        return false;
    }

    private static boolean validateEntry(Object value) {
        return value instanceof String entry && parseEntry(entry) != null;
    }

    private static List<WhitelistEntry> parseEntries(List<? extends String> values) {
        return values.stream()
              .map(Config::parseEntry)
              .filter(Objects::nonNull)
              .toList();
    }

    private static WhitelistEntry parseEntry(String value) {
        String entry = value.trim();
        boolean tag = entry.startsWith("#");
        String resourceName = tag ? entry.substring(1) : entry;
        ResourceLocation id = ResourceLocation.tryParse(resourceName);
        return id == null ? null : new WhitelistEntry(id, tag);
    }

    @SubscribeEvent
    static void onConfigChanged(ModConfigEvent event) {
        if (event.getConfig().getSpec() != SPEC) {
            return;
        }
        itemWhitelist = parseEntries(ITEM_WHITELIST.get());
        fluidWhitelist = parseEntries(FLUID_WHITELIST.get());
        chemicalWhitelist = parseEntries(CHEMICAL_WHITELIST.get());
    }

    private record WhitelistEntry(ResourceLocation id, boolean tag) {
    }

    private Config() {
    }
}
