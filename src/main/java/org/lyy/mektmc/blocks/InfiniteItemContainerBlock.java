package org.lyy.mektmc.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.lyy.mektmc.blockentity.InfiniteContentContainerBlockEntity;

public final class InfiniteItemContainerBlock extends InfiniteContentContainerBlock {

    public static final MapCodec<InfiniteItemContainerBlock> CODEC = simpleCodec(InfiniteItemContainerBlock::new);

    public InfiniteItemContainerBlock(BlockBehaviour.Properties properties) {
        super(ContainerType.ITEM, properties);
    }

    @Override
    protected MapCodec<? extends InfiniteContentContainerBlock> codec() {
        return CODEC;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
          Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.isEmpty()) {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }

        if (level.getBlockEntity(pos) instanceof InfiniteContentContainerBlockEntity blockEntity
              && blockEntity.isContainerType(ContainerType.ITEM)) {
            if (!level.isClientSide) {
                int originalCount = stack.getCount();
                ItemStack remainder = blockEntity.getItemHandler().insertItem(0, stack, false);
                int accepted = originalCount - remainder.getCount();
                if (accepted > 0 && !player.getAbilities().instabuild) {
                    stack.shrink(accepted);
                }
                if (accepted > 0) {
                    player.getInventory().setChanged();
                }
            }
            // A mismatched item must not run its normal use-on-block action against the container.
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }
}
