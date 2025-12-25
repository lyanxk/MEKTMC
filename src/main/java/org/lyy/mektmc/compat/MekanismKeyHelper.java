package org.lyy.mektmc.compat;

import java.util.function.Supplier;

import mekanism.api.chemical.Chemical;
import me.ramidzkh.mekae2.ae2.MekanismKey;

public final class MekanismKeyHelper {
    private MekanismKeyHelper() {}

    @SuppressWarnings("removal")
    public static Supplier<MekanismKey> gas(Supplier<? extends Chemical> gasSupplier) {
        return () -> {
            var gas = gasSupplier.get();
            var key = MekanismKey.of(gas.getStack(1));
            if (key == null) {
                throw new IllegalStateException("Gas supplier returned empty gas");
            }
            return key;
        };
    }
}
