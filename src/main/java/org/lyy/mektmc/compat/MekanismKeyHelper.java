package org.lyy.mektmc.compat;

import java.util.function.Supplier;

import mekanism.api.MekanismAPI;
import me.ramidzkh.mekae2.ae2.MekanismKey;
import net.minecraft.resources.ResourceLocation;

public final class MekanismKeyHelper {
    private MekanismKeyHelper() {}

    @SuppressWarnings("removal")
    public static Supplier<MekanismKey> gas(String registryName) {
        ResourceLocation id = ResourceLocation.parse(registryName);
        return () -> MekanismAPI.CHEMICAL_REGISTRY.getOptional(id)
              .filter(chemical -> chemical != MekanismAPI.EMPTY_CHEMICAL)
              .map(chemical -> MekanismKey.of(chemical.getStack(1)))
              .orElse(null);
    }
}
