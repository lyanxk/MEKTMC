package org.lyy.mektmc.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class InfiniteChemicalContainerBlock extends InfiniteContentContainerBlock {

    public static final MapCodec<InfiniteChemicalContainerBlock> CODEC = simpleCodec(InfiniteChemicalContainerBlock::new);

    public InfiniteChemicalContainerBlock(BlockBehaviour.Properties properties) {
        super(ContainerType.CHEMICAL, properties);
    }

    @Override
    protected MapCodec<? extends InfiniteContentContainerBlock> codec() {
        return CODEC;
    }
}
