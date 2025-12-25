package org.lyy.mektmc.ae;

import appeng.api.config.Actionable;
import appeng.api.networking.security.IActionSource;
import appeng.api.storage.MEStorage;
import appeng.api.storage.cells.CellState;
import appeng.api.storage.cells.ISaveProvider;
import appeng.api.storage.cells.StorageCell;
import appeng.api.stacks.AEKey;
import appeng.api.stacks.KeyCounter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import me.ramidzkh.mekae2.ae2.MekanismKey;
import org.lyy.mektmc.items.InfiniteGasCellItem;

public final class InfiniteGasCellInventory implements StorageCell {

    private final ItemStack cellStack;
    private final InfiniteGasCellItem cellItem;
    private final MekanismKey fixedKey;
    private final ISaveProvider saveProvider;

    public InfiniteGasCellInventory(ItemStack cellStack, InfiniteGasCellItem cellItem, ISaveProvider saveProvider) {
        this.cellStack = cellStack;
        this.cellItem = cellItem;
        this.fixedKey = cellItem.getFixedKey(cellStack);
        this.saveProvider = saveProvider; // 可能为 null
    }

    private boolean matches(AEKey what) {
        return cellItem.getFixedKey(cellStack).equals(what);
    }


    @Override
    public long insert(AEKey what, long amount, Actionable mode, IActionSource source) {
        MEStorage.checkPreconditions(what, amount, mode, source);
        if (amount == 0) return 0;
        return matches(what) ? amount : 0;
    }

    @Override
    public long extract(AEKey what, long amount, Actionable mode, IActionSource source) {
        MEStorage.checkPreconditions(what, amount, mode, source);
        if (amount == 0) return 0;
        return matches(what) ? amount : 0;
    }

    @Override
    public void getAvailableStacks(KeyCounter out) {
        out.add(fixedKey, Long.MAX_VALUE / 4);
    }

    @Override
    public Component getDescription() {
        return cellItem.getCellDescription();
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
        return 0.0;
    }

    @Override
    public void persist() {
        if (saveProvider != null) {
            saveProvider.saveChanges();
        }
    }
}
