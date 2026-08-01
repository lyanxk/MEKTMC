package org.lyy.mektmc.blocks;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.lyy.mektmc.blockentity.InfiniteContentContainerBlockEntity;

public abstract class InfiniteContentContainerBlock extends BaseEntityBlock {

    public enum ContainerType {
        ITEM("item"),
        FLUID("fluid"),
        CHEMICAL("chemical");

        private final String translationName;

        ContainerType(String translationName) {
            this.translationName = translationName;
        }

        public String translationName() {
            return translationName;
        }
    }

    private final ContainerType containerType;

    protected InfiniteContentContainerBlock(ContainerType containerType, BlockBehaviour.Properties properties) {
        super(properties);
        this.containerType = containerType;
    }

    public final ContainerType getContainerType() {
        return containerType;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new InfiniteContentContainerBlockEntity(pos, state);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        String tooltipPrefix = "tooltip.mektmc.infinite_" + containerType.translationName() + "_container";
        tooltip.add(Component.translatable(tooltipPrefix + ".lock").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable(tooltipPrefix + ".infinite").withStyle(ChatFormatting.AQUA));
        if (containerType != ContainerType.CHEMICAL) {
            tooltip.add(Component.translatable(tooltipPrefix + ".interaction").withStyle(ChatFormatting.GRAY));
        }
        tooltip.add(Component.translatable("tooltip.mektmc.infinite_container.clear")
              .withStyle(ChatFormatting.YELLOW));
        tooltip.add(Component.translatable("tooltip.mektmc.infinite_container.capacity", Integer.MAX_VALUE)
              .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.mektmc.infinite_container.immutable")
              .withStyle(ChatFormatting.DARK_GRAY));
    }
}
