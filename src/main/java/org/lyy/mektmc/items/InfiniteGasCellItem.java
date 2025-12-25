package org.lyy.mektmc.items;

import java.util.function.Supplier;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import me.ramidzkh.mekae2.ae2.MekanismKey;

public class InfiniteGasCellItem extends Item {

    private final Supplier<MekanismKey> keySupplier;
    private final Component desc;

    private MekanismKey cachedKey;

    public InfiniteGasCellItem(Supplier<MekanismKey> keySupplier, Properties props, Component desc) {
        super(props);
        this.keySupplier = keySupplier;
        this.desc = desc;
    }

    public MekanismKey getFixedKey(ItemStack stack) {
        if (cachedKey == null) {
            cachedKey = keySupplier.get(); // 只有在真正用到 cell 时才会触发
        }
        return cachedKey;
    }

    public Component getCellDescription() {
        return desc;
    }
}
