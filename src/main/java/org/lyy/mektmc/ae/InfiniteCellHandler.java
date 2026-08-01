package org.lyy.mektmc.ae;

import appeng.api.storage.cells.ICellHandler;
import appeng.api.storage.cells.ISaveProvider;
import appeng.api.storage.cells.StorageCell;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.lyy.mektmc.items.InfiniteCellItem;

public final class InfiniteCellHandler implements ICellHandler {

    public static final InfiniteCellHandler INSTANCE = new InfiniteCellHandler();

    private InfiniteCellHandler() {
    }

    @Override
    public boolean isCell(ItemStack stack) {
        return stack.getItem() instanceof InfiniteCellItem;
    }

    @Override
    public @Nullable StorageCell getCellInventory(ItemStack stack, @Nullable ISaveProvider host) {
        if (!(stack.getItem() instanceof InfiniteCellItem cellItem) || cellItem.getFixedKeys().isEmpty()) {
            return null;
        }
        return new InfiniteCellInventory(stack, cellItem, host);
    }
}
