package org.lyy.mektmc.registries;

import mekanism.generators.common.registries.GeneratorsChemicals;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;


import mekanism.common.registries.MekanismChemicals;
import org.lyy.mektmc.Mektmc;
import org.lyy.mektmc.compat.MekanismKeyHelper;
import org.lyy.mektmc.items.InfiniteGasCellItem;

public final class ItemRegistry {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Mektmc.MODID);


    public static final DeferredItem<Item> INFINITE_ETHYLENE_CELL =
            ITEMS.register("infinite_ethylene_cell", () ->
                    new InfiniteGasCellItem(
                            MekanismKeyHelper.gas(MekanismChemicals.ETHENE),
                            new Item.Properties().stacksTo(1),
                            Component.literal("∞ 无限乙烯单元")
                    )
            );
    public static final DeferredItem<Item> INFINITE_DEUTERIUM_CELL =
            ITEMS.register("infinite_deuterium_cell", () ->
                    new InfiniteGasCellItem(
                            MekanismKeyHelper.gas(GeneratorsChemicals.DEUTERIUM),
                            new Item.Properties().stacksTo(1),
                            Component.literal("无限氘单元")
                    )
            );

    public static final DeferredItem<Item> INFINITE_TRITIUM_CELL =
            ITEMS.register("infinite_tritium_cell", () ->
                    new InfiniteGasCellItem(
                            MekanismKeyHelper.gas(GeneratorsChemicals.TRITIUM),
                            new Item.Properties().stacksTo(1),
                            Component.literal("无限氚单元")
                    )
            );

    public static final DeferredItem<Item> INFINITE_D_T_FUEL_CELL =
            ITEMS.register("infinite_dt_fuel_cell", () ->
                    new InfiniteGasCellItem(
                            MekanismKeyHelper.gas(GeneratorsChemicals.FUSION_FUEL),
                            new Item.Properties().stacksTo(1),
                            Component.literal("无限氘氚燃料单元")
                    )
            );

    public static final DeferredItem<Item> INFINITE_BRINE_CELL =
            ITEMS.register("infinite_brine_cell", () ->
                    new InfiniteGasCellItem(
                            MekanismKeyHelper.gas(MekanismChemicals.BRINE),
                            new Item.Properties().stacksTo(1),
                            Component.literal("无限单元")
                    )
            );

    public static final DeferredItem<Item> INFINITE_LITHIUM_CELL =
            ITEMS.register("infinite_lithium_cell", () ->
                    new InfiniteGasCellItem(
                            MekanismKeyHelper.gas(MekanismChemicals.LITHIUM),
                            new Item.Properties().stacksTo(1),
                            Component.literal("无限锂单元")
                    )
            );

    public static final DeferredItem<Item> INFINITE_FISSILE_FUEL_CELL =
            ITEMS.register("infinite_fissile_fuel_cell", () ->
                    new InfiniteGasCellItem(
                            MekanismKeyHelper.gas(MekanismChemicals.FISSILE_FUEL),
                            new Item.Properties().stacksTo(1),
                            Component.literal("∞ 裂变燃料 Cell")
                    )
            );

    private ItemRegistry() {}
}