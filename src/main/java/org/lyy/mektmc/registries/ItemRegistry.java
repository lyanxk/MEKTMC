package org.lyy.mektmc.registries;

import appeng.api.stacks.AEKey;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import mekanism.common.registries.MekanismChemicals;
import mekanism.generators.common.registries.GeneratorsChemicals;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.lyy.mektmc.Mektmc;
import org.lyy.mektmc.compat.AEKeySuppliers;
import org.lyy.mektmc.compat.MekanismKeyHelper;
import org.lyy.mektmc.items.InfiniteCellItem;

public final class ItemRegistry {

    private static final String[] COLORS = {
          "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray",
          "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black"
    };
    private static final String[] WOOD_CONTENTS = {
          "minecraft:oak_log", "minecraft:stripped_oak_log", "minecraft:oak_planks",
          "minecraft:spruce_log", "minecraft:stripped_spruce_log", "minecraft:spruce_planks",
          "minecraft:birch_log", "minecraft:stripped_birch_log", "minecraft:birch_planks",
          "minecraft:jungle_log", "minecraft:stripped_jungle_log", "minecraft:jungle_planks",
          "minecraft:acacia_log", "minecraft:stripped_acacia_log", "minecraft:acacia_planks",
          "minecraft:dark_oak_log", "minecraft:stripped_dark_oak_log", "minecraft:dark_oak_planks",
          "minecraft:mangrove_log", "minecraft:stripped_mangrove_log", "minecraft:mangrove_planks",
          "minecraft:cherry_log", "minecraft:stripped_cherry_log", "minecraft:cherry_planks",
          "minecraft:crimson_stem", "minecraft:stripped_crimson_stem", "minecraft:crimson_planks",
          "minecraft:warped_stem", "minecraft:stripped_warped_stem", "minecraft:warped_planks",
          "minecraft:bamboo_block", "minecraft:stripped_bamboo_block", "minecraft:bamboo_planks"
    };
    private static final String[] STONE_CONTENTS = {
          "minecraft:stone", "minecraft:deepslate", "minecraft:andesite", "minecraft:diorite", "minecraft:granite"
    };
    private static final String[] ORE_CONTENTS = {
          "minecraft:iron_ore", "minecraft:copper_ore", "minecraft:gold_ore", "minecraft:coal_ore",
          "minecraft:redstone_ore", "minecraft:lapis_ore", "minecraft:diamond_ore", "minecraft:emerald_ore",
          "mekanism:osmium_ore", "mekanism:tin_ore", "mekanism:lead_ore", "mekanism:uranium_ore",
          "mekanism:fluorite_ore", "create:zinc_ore"
    };

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Mektmc.MODID);

    public static final DeferredItem<BlockItem> INFINITE_ITEM_CONTAINER =
          ITEMS.registerSimpleBlockItem(BlockRegistry.INFINITE_ITEM_CONTAINER);
    public static final DeferredItem<BlockItem> INFINITE_FLUID_CONTAINER =
          ITEMS.registerSimpleBlockItem(BlockRegistry.INFINITE_FLUID_CONTAINER);
    public static final DeferredItem<BlockItem> INFINITE_CHEMICAL_CONTAINER =
          ITEMS.registerSimpleBlockItem(BlockRegistry.INFINITE_CHEMICAL_CONTAINER);
    public static final DeferredItem<BlockItem> COMPRESSED_GLASS =
          ITEMS.registerSimpleBlockItem(BlockRegistry.COMPRESSED_GLASS);
    public static final DeferredItem<BlockItem> DOUBLE_COMPRESSED_GLASS =
          ITEMS.registerSimpleBlockItem(BlockRegistry.DOUBLE_COMPRESSED_GLASS);
    public static final DeferredItem<BlockItem> TRIPLE_COMPRESSED_GLASS =
          ITEMS.registerSimpleBlockItem(BlockRegistry.TRIPLE_COMPRESSED_GLASS);

    public static final DeferredItem<Item> INFINITY_DIRT_CELL = itemCell("infinity_dirt_cell", "minecraft:dirt");
    public static final DeferredItem<Item> INFINITY_SAND_CELL = itemCell("infinity_sand_cell", "minecraft:sand");
    public static final DeferredItem<Item> INFINITY_GRAVEL_CELL = itemCell("infinity_gravel_cell", "minecraft:gravel");
    public static final DeferredItem<Item> INFINITY_LAVA_CELL = fluidCell("infinity_lava_cell", "minecraft:lava");

    public static final DeferredItem<Item> INFINITIES_WOOD_CELL = itemCell("infinities_wood_cell", WOOD_CONTENTS);
    public static final DeferredItem<Item> INFINITIES_CONCRETE_CELL =
          itemCell("infinities_concrete_cell", coloredItems("_concrete"));
    public static final DeferredItem<Item> INFINITIES_DYE_CELL =
          itemCell("infinities_dye_cell", coloredItems("_dye"));
    public static final DeferredItem<Item> INFINITIES_STONE_CELL = itemCell("infinities_stone_cell", STONE_CONTENTS);
    public static final DeferredItem<Item> INFINITIES_NON_ORES_CELL =
          registerCell("infinities_non_ores_cell", nonOreKeys());

    public static final DeferredItem<Item> INFINITIES_IRON_CELL = itemCell("infinities_iron_cell", "minecraft:iron_ore");
    public static final DeferredItem<Item> INFINITIES_COPPER_CELL = itemCell("infinities_copper_cell", "minecraft:copper_ore");
    public static final DeferredItem<Item> INFINITIES_GOLD_CELL = itemCell("infinities_gold_cell", "minecraft:gold_ore");
    public static final DeferredItem<Item> INFINITIES_COAL_CELL = itemCell("infinities_coal_cell", "minecraft:coal_ore");
    public static final DeferredItem<Item> INFINITIES_REDSTONE_CELL = itemCell("infinities_redstone_cell", "minecraft:redstone_ore");
    public static final DeferredItem<Item> INFINITIES_LAPIS_CELL = itemCell("infinities_lapis_cell", "minecraft:lapis_ore");
    public static final DeferredItem<Item> INFINITIES_DIAMOND_CELL = itemCell("infinities_diamond_cell", "minecraft:diamond_ore");
    public static final DeferredItem<Item> INFINITIES_EMERALD_CELL = itemCell("infinities_emerald_cell", "minecraft:emerald_ore");
    public static final DeferredItem<Item> INFINITIES_MEK_OSMIUM_CELL = itemCell("infinities_mek_osmium_cell", "mekanism:osmium_ore");
    public static final DeferredItem<Item> INFINITIES_MEK_TIN_CELL = itemCell("infinities_mek_tin_cell", "mekanism:tin_ore");
    public static final DeferredItem<Item> INFINITIES_MEK_LEAD_CELL = itemCell("infinities_mek_lead_cell", "mekanism:lead_ore");
    public static final DeferredItem<Item> INFINITIES_MEK_URANIUM_CELL = itemCell("infinities_mek_uranium_cell", "mekanism:uranium_ore");
    public static final DeferredItem<Item> INFINITIES_MEK_FLUORITE_CELL = itemCell("infinities_mek_fluorite_cell", "mekanism:fluorite_ore");
    public static final DeferredItem<Item> INFINITIES_CREATE_ZINC_CELL = itemCell("infinities_create_zinc_cell", "create:zinc_ore");

    public static final DeferredItem<Item> INFINITIES_AE_CERTUS_QUARTZ_CELL =
          itemCell("infinities_ae_certus_quartz_cell", "ae2:certus_quartz_crystal");
    public static final DeferredItem<Item> INFINITIES_AE_CHARGED_CERTUS_QUARTZ_CELL =
          itemCell("infinities_ae_charged_certus_quartz_cell", "ae2:charged_certus_quartz_crystal");
    public static final DeferredItem<Item> INFINITIES_AE_FLUIX_CELL =
          itemCell("infinities_ae_fluix_cell", "ae2:fluix_crystal");
    public static final DeferredItem<Item> INFINITIES_EXTENDEDAE_ENTRO_CELL =
          itemCell("infinities_extendedae_entro_cell", "extendedae:entro_crystal");
    public static final DeferredItem<Item> INFINITIES_AE_ALL_CRYSTALS_CELL = itemCell(
          "infinities_ae_all_crystals_cell",
          "ae2:certus_quartz_crystal", "ae2:charged_certus_quartz_crystal", "ae2:fluix_crystal",
          "extendedae:entro_crystal"
    );
    public static final DeferredItem<Item> INFINITIES_ALL_ORES_CELL = itemCell("infinities_all_ores_cell", ORE_CONTENTS);

    public static final DeferredItem<Item> INFINITE_ETHYLENE_CELL =
          chemicalCell("infinite_ethylene_cell", MekanismKeyHelper.gas(MekanismChemicals.ETHENE));
    public static final DeferredItem<Item> INFINITE_DEUTERIUM_CELL =
          chemicalCell("infinite_deuterium_cell", MekanismKeyHelper.gas(GeneratorsChemicals.DEUTERIUM));
    public static final DeferredItem<Item> INFINITE_TRITIUM_CELL =
          chemicalCell("infinite_tritium_cell", MekanismKeyHelper.gas(GeneratorsChemicals.TRITIUM));
    public static final DeferredItem<Item> INFINITE_D_T_FUEL_CELL =
          chemicalCell("infinite_dt_fuel_cell", MekanismKeyHelper.gas(GeneratorsChemicals.FUSION_FUEL));
    public static final DeferredItem<Item> INFINITE_BRINE_CELL =
          chemicalCell("infinite_brine_cell", MekanismKeyHelper.gas(MekanismChemicals.BRINE));
    public static final DeferredItem<Item> INFINITE_LITHIUM_CELL =
          chemicalCell("infinite_lithium_cell", MekanismKeyHelper.gas(MekanismChemicals.LITHIUM));
    public static final DeferredItem<Item> INFINITE_FISSILE_FUEL_CELL =
          chemicalCell("infinite_fissile_fuel_cell", MekanismKeyHelper.gas(MekanismChemicals.FISSILE_FUEL));

    private static DeferredItem<Item> itemCell(String name, String... registryNames) {
        return registerCell(name, itemKeys(registryNames));
    }

    private static DeferredItem<Item> fluidCell(String name, String registryName) {
        return registerCell(name, List.of(AEKeySuppliers.fluid(registryName)));
    }

    private static DeferredItem<Item> chemicalCell(String name, Supplier<? extends AEKey> keySupplier) {
        return registerCell(name, List.of(keySupplier));
    }

    private static DeferredItem<Item> registerCell(String name, List<Supplier<? extends AEKey>> keySuppliers) {
        return ITEMS.register(name, () -> new InfiniteCellItem(keySuppliers, new Item.Properties().stacksTo(1)));
    }

    private static List<Supplier<? extends AEKey>> itemKeys(String... registryNames) {
        List<Supplier<? extends AEKey>> keys = new ArrayList<>(registryNames.length);
        for (String registryName : registryNames) {
            keys.add(AEKeySuppliers.item(registryName));
        }
        return List.copyOf(keys);
    }

    private static List<Supplier<? extends AEKey>> nonOreKeys() {
        List<Supplier<? extends AEKey>> keys = new ArrayList<>();
        keys.addAll(itemKeys("minecraft:dirt", "minecraft:sand", "minecraft:gravel"));
        keys.add(AEKeySuppliers.fluid("minecraft:lava"));
        keys.addAll(itemKeys(WOOD_CONTENTS));
        keys.addAll(itemKeys(coloredItems("_dye")));
        keys.addAll(itemKeys(coloredItems("_concrete")));
        keys.addAll(itemKeys(STONE_CONTENTS));
        return List.copyOf(keys);
    }

    private static String[] coloredItems(String suffix) {
        String[] items = new String[COLORS.length];
        for (int i = 0; i < COLORS.length; i++) {
            items[i] = "minecraft:" + COLORS[i] + suffix;
        }
        return items;
    }

    private ItemRegistry() {
    }
}
