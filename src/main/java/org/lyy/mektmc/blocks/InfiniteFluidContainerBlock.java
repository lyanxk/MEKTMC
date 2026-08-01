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
import net.neoforged.neoforge.fluids.FluidUtil;

public final class InfiniteFluidContainerBlock extends InfiniteContentContainerBlock {

    public static final MapCodec<InfiniteFluidContainerBlock> CODEC = simpleCodec(InfiniteFluidContainerBlock::new);

    public InfiniteFluidContainerBlock(BlockBehaviour.Properties properties) {
        super(ContainerType.FLUID, properties);
    }

    @Override
    protected MapCodec<? extends InfiniteContentContainerBlock> codec() {
        return CODEC;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
          Player player, InteractionHand hand, BlockHitResult hitResult) {
        // Some fluid containers only expose their capability when their stack size is one.
        if (stack.isEmpty() || FluidUtil.getFluidHandler(stack.copyWithCount(1)).isEmpty()) {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }

        if (!level.isClientSide
              && FluidUtil.interactWithFluidHandler(player, hand, level, pos, hitResult.getDirection())) {
            player.getInventory().setChanged();
        }
        // Prevent a rejected or mismatched bucket from placing fluid beside the container.
        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }
}
