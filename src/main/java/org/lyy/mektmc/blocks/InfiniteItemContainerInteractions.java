package org.lyy.mektmc.blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.lyy.mektmc.blockentity.InfiniteContentContainerBlockEntity;

public final class InfiniteItemContainerInteractions {

    private static final Map<UUID, BlockPos> ACTIVE_EXTRACTIONS = new HashMap<>();

    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getEntity().isShiftKeyDown()
              || !(event.getLevel().getBlockState(event.getPos()).getBlock() instanceof InfiniteContentContainerBlock)) {
            return;
        }

        // Clearing takes the whole interaction so the held item or fluid container cannot immediately relock it.
        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.sidedSuccess(event.getLevel().isClientSide));
        // Clear on the client for immediate visual feedback, then let the server perform and synchronize the same change.
        if (event.getLevel().getBlockEntity(event.getPos()) instanceof InfiniteContentContainerBlockEntity blockEntity) {
            blockEntity.clearLockedContent();
        }
    }

    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        Level level = event.getLevel();
        if (level.isClientSide) {
            return;
        }

        Player player = event.getEntity();
        BlockPos pos = event.getPos();
        if (event.getAction() != PlayerInteractEvent.LeftClickBlock.Action.START) {
            ACTIVE_EXTRACTIONS.remove(player.getUUID());
            return;
        }

        if (!(level.getBlockState(pos).getBlock() instanceof InfiniteItemContainerBlock)
              || !(level.getBlockEntity(pos) instanceof InfiniteContentContainerBlockEntity blockEntity)
              || blockEntity.getRenderItem().isEmpty()) {
            ACTIVE_EXTRACTIONS.remove(player.getUUID());
            return;
        }

        // Repeated START packets are generated while the mouse button is held. Only the first one dispenses.
        event.setCanceled(true);
        BlockPos previousPos = ACTIVE_EXTRACTIONS.put(player.getUUID(), pos.immutable());
        if (pos.equals(previousPos)) {
            return;
        }

        ItemStack storedItem = blockEntity.getRenderItem();
        int amount = player.isShiftKeyDown() ? storedItem.getMaxStackSize() : 1;
        ItemStack extracted = blockEntity.getItemHandler().extractItem(0, amount, false);
        if (extracted.isEmpty()) {
            return;
        }

        ItemStack remainder = extracted.copy();
        player.getInventory().add(remainder);
        player.getInventory().setChanged();
        if (!remainder.isEmpty()) {
            dropInFrontOfClickedFace(level, pos, event.getFace(), player, remainder);
        }
    }

    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        ACTIVE_EXTRACTIONS.remove(event.getEntity().getUUID());
    }

    private static void dropInFrontOfClickedFace(Level level, BlockPos pos, Direction clickedFace, Player player,
          ItemStack stack) {
        Direction dropDirection = clickedFace == null ? player.getDirection().getOpposite() : clickedFace;
        BlockPos dropPos = pos.relative(dropDirection);
        ItemEntity droppedItem = new ItemEntity(level,
              dropPos.getX() + 0.5D,
              dropPos.getY() + 0.5D,
              dropPos.getZ() + 0.5D,
              stack.copy(),
              dropDirection.getStepX() * 0.08D,
              dropDirection.getStepY() * 0.08D,
              dropDirection.getStepZ() * 0.08D);
        droppedItem.setDefaultPickUpDelay();
        level.addFreshEntity(droppedItem);
    }

    private InfiniteItemContainerInteractions() {
    }
}
