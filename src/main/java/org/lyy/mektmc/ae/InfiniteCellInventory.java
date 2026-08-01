package org.lyy.mektmc.ae;

import appeng.api.config.Actionable;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.AEKey;
import appeng.api.stacks.KeyCounter;
import appeng.api.storage.MEStorage;
import appeng.api.storage.cells.CellState;
import appeng.api.storage.cells.ISaveProvider;
import appeng.api.storage.cells.StorageCell;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.lyy.mektmc.items.InfiniteCellItem;

public final class InfiniteCellInventory implements StorageCell {

    private static final long DISPLAY_AMOUNT = Long.MAX_VALUE / 4;

    private final ItemStack cellStack;
    private final List<AEKey> fixedKeys;
    private final @Nullable ISaveProvider saveProvider;

    public InfiniteCellInventory(ItemStack cellStack, InfiniteCellItem cellItem, @Nullable ISaveProvider saveProvider) {
        this.cellStack = cellStack;
        this.fixedKeys = cellItem.getFixedKeys();
        this.saveProvider = saveProvider;
    }

    private boolean matches(AEKey key) {
        return fixedKeys.contains(key);
    }

    @Override
    public long insert(AEKey what, long amount, Actionable mode, IActionSource source) {
        MEStorage.checkPreconditions(what, amount, mode, source);
        return matches(what) ? amount : 0;
    }

    @Override
    public long extract(AEKey what, long amount, Actionable mode, IActionSource source) {
        MEStorage.checkPreconditions(what, amount, mode, source);
        return matches(what) ? amount : 0;
    }

    @Override
    public void getAvailableStacks(KeyCounter output) {
        fixedKeys.forEach(key -> output.add(key, DISPLAY_AMOUNT));
    }

    @Override
    public Component getDescription() {
        return cellStack.getHoverName();
    }

    @Override
    public boolean isPreferredStorageFor(AEKey what, IActionSource source) {
        return matches(what);
    }

    @Override
    public CellState getStatus() {
        return CellState.NOT_EMPTY;
    }

    @Override
    public double getIdleDrain() {
        return 0;
    }

    @Override
    public void persist() {
        if (saveProvider != null) {
            saveProvider.saveChanges();
        }
    }
}
