package org.lyy.mektmc.ae;

import appeng.api.storage.cells.ICellHandler;
import appeng.api.storage.cells.ISaveProvider;
import appeng.api.storage.cells.StorageCell;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.lyy.mektmc.items.InfiniteGasCellItem;

public final class InfiniteGasCellHandler implements ICellHandler {

    public static final InfiniteGasCellHandler INSTANCE = new InfiniteGasCellHandler();

    private InfiniteGasCellHandler() {}

    @Override
    public boolean isCell(ItemStack is) {
        return is.getItem() instanceof InfiniteGasCellItem;
    }

    @Override
    public @Nullable StorageCell getCellInventory(ItemStack is, @Nullable ISaveProvider host) {
        if (!(is.getItem() instanceof InfiniteGasCellItem cellItem)) {
            return null;
        }
        return new InfiniteGasCellInventory(is, cellItem, host);
    }
}
