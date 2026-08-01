package org.lyy.mektmc.blockentity;

import mekanism.api.Action;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;
import org.lyy.mektmc.Config;
import org.lyy.mektmc.blocks.InfiniteContentContainerBlock;
import org.lyy.mektmc.blocks.InfiniteContentContainerBlock.ContainerType;
import org.lyy.mektmc.registries.BlockEntityRegistry;

/**
 * Permanently type-locked infinite storage. The placed block decides whether this entity accepts an item,
 * a fluid, or a chemical. Its exposed amount is always {@link Integer#MAX_VALUE} and transfers never reduce it.
 */
public final class InfiniteContentContainerBlockEntity extends BlockEntity {

    public static final int CAPACITY = Integer.MAX_VALUE;

    private static final String LOCKED_ITEM_TAG = "locked_item";
    private static final String LOCKED_FLUID_TAG = "locked_fluid";
    private static final String LOCKED_CHEMICAL_TAG = "locked_chemical";
    // Keep an explicit value in update tags even when the container is empty, so clients replace stale render data.
    private static final String HAS_LOCKED_CONTENT_TAG = "has_locked_content";

    private ItemStack lockedItem = ItemStack.EMPTY;
    private FluidStack lockedFluid = FluidStack.EMPTY;
    private ChemicalStack lockedChemical = ChemicalStack.EMPTY;

    private final IItemHandler itemHandler = new InfiniteItemHandler();
    private final IFluidHandler fluidHandler = new InfiniteFluidHandler();
    private final IChemicalHandler chemicalHandler = new InfiniteChemicalHandler();

    public InfiniteContentContainerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.INFINITE_CONTAINER.get(), pos, state);
    }

    public ContainerType getContainerType() {
        if (getBlockState().getBlock() instanceof InfiniteContentContainerBlock containerBlock) {
            return containerBlock.getContainerType();
        }
        throw new IllegalStateException("Infinite container block entity has an incompatible block state");
    }

    public boolean isContainerType(ContainerType type) {
        return getContainerType() == type;
    }

    public IItemHandler getItemHandler() {
        return itemHandler;
    }

    public IFluidHandler getFluidHandler() {
        return fluidHandler;
    }

    public IChemicalHandler getChemicalHandler() {
        return chemicalHandler;
    }

    public ItemStack getRenderItem() {
        return lockedItem.isEmpty() ? ItemStack.EMPTY : lockedItem.copyWithCount(1);
    }

    public FluidStack getRenderFluid() {
        return lockedFluid.isEmpty() ? FluidStack.EMPTY : lockedFluid.copyWithAmount(CAPACITY);
    }

    public ChemicalStack getRenderChemical() {
        return lockedChemical.isEmpty() ? ChemicalStack.EMPTY : lockedChemical.copyWithAmount(CAPACITY);
    }

    public boolean clearLockedContent() {
        if (lockedItem.isEmpty() && lockedFluid.isEmpty() && lockedChemical.isEmpty()) {
            return false;
        }
        lockedItem = ItemStack.EMPTY;
        lockedFluid = FluidStack.EMPTY;
        lockedChemical = ChemicalStack.EMPTY;
        markContentsChanged();
        return true;
    }

    private boolean canAcceptItem(ItemStack stack) {
        return isContainerType(ContainerType.ITEM)
              && !stack.isEmpty()
              && Config.isItemAllowed(stack)
              && (lockedItem.isEmpty() || ItemStack.isSameItemSameComponents(lockedItem, stack));
    }

    private boolean canAcceptFluid(FluidStack stack) {
        return isContainerType(ContainerType.FLUID)
              && !stack.isEmpty()
              && Config.isFluidAllowed(stack)
              && (lockedFluid.isEmpty() || FluidStack.isSameFluidSameComponents(lockedFluid, stack));
    }

    private boolean canAcceptChemical(ChemicalStack stack) {
        return isContainerType(ContainerType.CHEMICAL)
              && !stack.isEmpty()
              && Config.isChemicalAllowed(stack)
              && (lockedChemical.isEmpty() || ChemicalStack.isSameChemical(lockedChemical, stack));
    }

    private void lockItem(ItemStack stack) {
        if (!lockedItem.isEmpty()) {
            return;
        }
        lockedItem = stack.copyWithCount(1);
        markContentsChanged();
    }

    private void lockFluid(FluidStack stack) {
        if (!lockedFluid.isEmpty()) {
            return;
        }
        lockedFluid = stack.copyWithAmount(CAPACITY);
        markContentsChanged();
    }

    private void lockChemical(ChemicalStack stack) {
        if (!lockedChemical.isEmpty()) {
            return;
        }
        lockedChemical = stack.copyWithAmount(CAPACITY);
        markContentsChanged();
    }

    private void markContentsChanged() {
        setChanged();
        if (level != null && !level.isClientSide) {
            BlockState state = getBlockState();
            level.sendBlockUpdated(worldPosition, state, state, Block.UPDATE_CLIENTS);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        lockedItem = ItemStack.EMPTY;
        lockedFluid = FluidStack.EMPTY;
        lockedChemical = ChemicalStack.EMPTY;

        switch (getContainerType()) {
            case ITEM -> {
                if (tag.contains(LOCKED_ITEM_TAG, Tag.TAG_COMPOUND)) {
                    ItemStack loaded = ItemStack.parseOptional(provider, tag.getCompound(LOCKED_ITEM_TAG));
                    if (!loaded.isEmpty()) {
                        lockedItem = loaded.copyWithCount(1);
                    }
                }
            }
            case FLUID -> {
                if (tag.contains(LOCKED_FLUID_TAG, Tag.TAG_COMPOUND)) {
                    FluidStack loaded = FluidStack.parseOptional(provider, tag.getCompound(LOCKED_FLUID_TAG));
                    if (!loaded.isEmpty()) {
                        lockedFluid = loaded.copyWithAmount(CAPACITY);
                    }
                }
            }
            case CHEMICAL -> {
                if (tag.contains(LOCKED_CHEMICAL_TAG, Tag.TAG_COMPOUND)) {
                    ChemicalStack loaded = ChemicalStack.parseOptional(provider, tag.getCompound(LOCKED_CHEMICAL_TAG));
                    if (!loaded.isEmpty()) {
                        lockedChemical = loaded.copyWithAmount(CAPACITY);
                    }
                }
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putBoolean(HAS_LOCKED_CONTENT_TAG,
              !lockedItem.isEmpty() || !lockedFluid.isEmpty() || !lockedChemical.isEmpty());
        switch (getContainerType()) {
            case ITEM -> {
                if (!lockedItem.isEmpty()) {
                    tag.put(LOCKED_ITEM_TAG, lockedItem.saveOptional(provider));
                }
            }
            case FLUID -> {
                if (!lockedFluid.isEmpty()) {
                    tag.put(LOCKED_FLUID_TAG, lockedFluid.saveOptional(provider));
                }
            }
            case CHEMICAL -> {
                if (!lockedChemical.isEmpty()) {
                    tag.put(LOCKED_CHEMICAL_TAG, lockedChemical.saveOptional(provider));
                }
            }
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return saveWithoutMetadata(provider);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    private final class InfiniteItemHandler implements IItemHandler {

        @Override
        public int getSlots() {
            return 1;
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
            return slot == 0 && !lockedItem.isEmpty()
                  ? lockedItem.copyWithCount(CAPACITY)
                  : ItemStack.EMPTY;
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (slot != 0 || !canAcceptItem(stack)) {
                return stack;
            }
            int accepted = Math.min(stack.getCount(), CAPACITY);
            if (accepted > 0 && !simulate && lockedItem.isEmpty()) {
                lockItem(stack);
            }
            int remainder = stack.getCount() - accepted;
            return remainder <= 0 ? ItemStack.EMPTY : stack.copyWithCount(remainder);
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (slot != 0 || amount <= 0 || lockedItem.isEmpty()) {
                return ItemStack.EMPTY;
            }
            return lockedItem.copyWithCount(Math.min(amount, CAPACITY));
        }

        @Override
        public int getSlotLimit(int slot) {
            return slot == 0 ? CAPACITY : 0;
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return slot == 0 && canAcceptItem(stack);
        }
    }

    private final class InfiniteFluidHandler implements IFluidHandler {

        @Override
        public int getTanks() {
            return 1;
        }

        @Override
        public FluidStack getFluidInTank(int tank) {
            return tank == 0 ? getRenderFluid() : FluidStack.EMPTY;
        }

        @Override
        public int getTankCapacity(int tank) {
            return tank == 0 ? CAPACITY : 0;
        }

        @Override
        public boolean isFluidValid(int tank, FluidStack stack) {
            return tank == 0 && canAcceptFluid(stack);
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            if (!canAcceptFluid(resource)) {
                return 0;
            }
            int accepted = Math.min(resource.getAmount(), CAPACITY);
            if (accepted > 0 && action.execute() && lockedFluid.isEmpty()) {
                lockFluid(resource);
            }
            return accepted;
        }

        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            if (resource.isEmpty() || lockedFluid.isEmpty()
                  || !FluidStack.isSameFluidSameComponents(lockedFluid, resource)) {
                return FluidStack.EMPTY;
            }
            return lockedFluid.copyWithAmount(Math.min(resource.getAmount(), CAPACITY));
        }

        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            if (maxDrain <= 0 || lockedFluid.isEmpty()) {
                return FluidStack.EMPTY;
            }
            return lockedFluid.copyWithAmount(Math.min(maxDrain, CAPACITY));
        }
    }

    private final class InfiniteChemicalHandler implements IChemicalHandler {

        @Override
        public int getChemicalTanks() {
            return 1;
        }

        @Override
        public ChemicalStack getChemicalInTank(int tank) {
            return tank == 0 ? getRenderChemical() : ChemicalStack.EMPTY;
        }

        @Override
        public void setChemicalInTank(int tank, ChemicalStack stack) {
            if (tank == 0 && canAcceptChemical(stack) && lockedChemical.isEmpty()) {
                lockChemical(stack);
            }
        }

        @Override
        public long getChemicalTankCapacity(int tank) {
            return tank == 0 ? CAPACITY : 0;
        }

        @Override
        public boolean isValid(int tank, ChemicalStack stack) {
            return tank == 0 && canAcceptChemical(stack);
        }

        @Override
        public ChemicalStack insertChemical(int tank, ChemicalStack stack, Action action) {
            if (tank != 0 || !canAcceptChemical(stack)) {
                return stack;
            }
            long accepted = Math.min(stack.getAmount(), (long) CAPACITY);
            if (accepted > 0 && action.execute() && lockedChemical.isEmpty()) {
                lockChemical(stack);
            }
            long remainder = stack.getAmount() - accepted;
            return remainder == 0 ? ChemicalStack.EMPTY : stack.copyWithAmount(remainder);
        }

        @Override
        public ChemicalStack extractChemical(int tank, long amount, Action action) {
            if (tank != 0 || amount <= 0 || lockedChemical.isEmpty()) {
                return ChemicalStack.EMPTY;
            }
            return lockedChemical.copyWithAmount(Math.min(amount, (long) CAPACITY));
        }
    }
}
