package org.lyy.mektmc.items;

import appeng.api.stacks.AEKey;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.world.item.Item;

public final class InfiniteCellItem extends Item {

    private final List<Supplier<? extends AEKey>> keySuppliers;
    private volatile List<AEKey> cachedKeys;

    public InfiniteCellItem(List<Supplier<? extends AEKey>> keySuppliers, Properties properties) {
        super(properties);
        this.keySuppliers = List.copyOf(keySuppliers);
    }

    public List<AEKey> getFixedKeys() {
        List<AEKey> keys = cachedKeys;
        if (keys == null) {
            synchronized (this) {
                keys = cachedKeys;
                if (keys == null) {
                    LinkedHashSet<AEKey> resolvedKeys = new LinkedHashSet<>();
                    for (Supplier<? extends AEKey> keySupplier : keySuppliers) {
                        AEKey key = keySupplier.get();
                        if (key != null) {
                            resolvedKeys.add(key);
                        }
                    }
                    keys = List.copyOf(resolvedKeys);
                    cachedKeys = keys;
                }
            }
        }
        return keys;
    }
}
