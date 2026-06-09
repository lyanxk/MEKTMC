package org.lyy.mektmc.registries;

import mekanism.common.registries.MekanismChemicals;
import mekanism.generators.common.registries.GeneratorsChemicals;
import appeng.items.parts.PartItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.lyy.mektmc.Mektmc;
import org.lyy.mektmc.compat.MekanismKeyHelper;
import org.lyy.mektmc.items.CategoryDiskItem;
import org.lyy.mektmc.items.InfiniteGasCellItem;
import org.lyy.mektmc.parts.CategorizedTerminalPart;

public final class ItemRegistry {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Mektmc.MODID);

    public static final DeferredItem<Item> CATEGORY_DISK =
            ITEMS.register("category_disk", () -> new CategoryDiskItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CATEGORY_INDEX =
            ITEMS.register("category_index", () -> new BlockItem(BlockRegistry.CATEGORY_INDEX.get(), new Item.Properties()));

    public static final DeferredItem<Item> CATEGORIZED_TERMINAL =
            ITEMS.register("categorized_terminal", () -> new PartItem<>(new Item.Properties(), CategorizedTerminalPart.class, CategorizedTerminalPart::new));

    public static final DeferredItem<Item> INFINITE_ETHYLENE_CELL =
            ITEMS.register("infinite_ethylene_cell", () ->
                    new InfiniteGasCellItem(
                            MekanismKeyHelper.gas(MekanismChemicals.ETHENE),
                            new Item.Properties().stacksTo(1),
                            Component.literal("ME Infinite Ethylene Cell")
                    )
            );

    public static final DeferredItem<Item> INFINITE_DEUTERIUM_CELL =
            ITEMS.register("infinite_deuterium_cell", () ->
                    new InfiniteGasCellItem(
                            MekanismKeyHelper.gas(GeneratorsChemicals.DEUTERIUM),
                            new Item.Properties().stacksTo(1),
                            Component.literal("ME Infinite Deuterium Cell")
                    )
            );

    public static final DeferredItem<Item> INFINITE_TRITIUM_CELL =
            ITEMS.register("infinite_tritium_cell", () ->
                    new InfiniteGasCellItem(
                            MekanismKeyHelper.gas(GeneratorsChemicals.TRITIUM),
                            new Item.Properties().stacksTo(1),
                            Component.literal("ME Infinite Tritium Cell")
                    )
            );

    public static final DeferredItem<Item> INFINITE_D_T_FUEL_CELL =
            ITEMS.register("infinite_dt_fuel_cell", () ->
                    new InfiniteGasCellItem(
                            MekanismKeyHelper.gas(GeneratorsChemicals.FUSION_FUEL),
                            new Item.Properties().stacksTo(1),
                            Component.literal("ME Infinite D-T Fuel Cell")
                    )
            );

    public static final DeferredItem<Item> INFINITE_BRINE_CELL =
            ITEMS.register("infinite_brine_cell", () ->
                    new InfiniteGasCellItem(
                            MekanismKeyHelper.gas(MekanismChemicals.BRINE),
                            new Item.Properties().stacksTo(1),
                            Component.literal("ME Infinite Brine Cell")
                    )
            );

    public static final DeferredItem<Item> INFINITE_LITHIUM_CELL =
            ITEMS.register("infinite_lithium_cell", () ->
                    new InfiniteGasCellItem(
                            MekanismKeyHelper.gas(MekanismChemicals.LITHIUM),
                            new Item.Properties().stacksTo(1),
                            Component.literal("ME Infinite Lithium Cell")
                    )
            );

    public static final DeferredItem<Item> INFINITE_FISSILE_FUEL_CELL =
            ITEMS.register("infinite_fissile_fuel_cell", () ->
                    new InfiniteGasCellItem(
                            MekanismKeyHelper.gas(MekanismChemicals.FISSILE_FUEL),
                            new Item.Properties().stacksTo(1),
                            Component.literal("ME Infinite Fissile Fuel Cell")
                    )
            );

    private ItemRegistry() {}
}
