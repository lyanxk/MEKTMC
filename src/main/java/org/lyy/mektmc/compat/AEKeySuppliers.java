package org.lyy.mektmc.compat;

import appeng.api.stacks.AEFluidKey;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;

public final class AEKeySuppliers {

    public static Supplier<AEKey> item(String registryName) {
        ResourceLocation id = ResourceLocation.parse(registryName);
        return () -> BuiltInRegistries.ITEM.getOptional(id)
              .filter(item -> item != Items.AIR)
              .map(AEItemKey::of)
              .orElse(null);
    }

    public static Supplier<AEKey> fluid(String registryName) {
        ResourceLocation id = ResourceLocation.parse(registryName);
        return () -> BuiltInRegistries.FLUID.getOptional(id)
              .filter(fluid -> fluid != Fluids.EMPTY)
              .map(AEFluidKey::of)
              .orElse(null);
    }

    private AEKeySuppliers() {
    }
}
