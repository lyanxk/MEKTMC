package org.lyy.mektmc.registries;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.lyy.mektmc.Mektmc;
import org.lyy.mektmc.blocks.InfiniteChemicalContainerBlock;
import org.lyy.mektmc.blocks.InfiniteFluidContainerBlock;
import org.lyy.mektmc.blocks.InfiniteItemContainerBlock;

public final class BlockRegistry {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Mektmc.MODID);

    public static final DeferredBlock<InfiniteItemContainerBlock> INFINITE_ITEM_CONTAINER =
          BLOCKS.registerBlock("infinite_item_container", InfiniteItemContainerBlock::new, containerProperties());

    public static final DeferredBlock<InfiniteFluidContainerBlock> INFINITE_FLUID_CONTAINER =
          BLOCKS.registerBlock("infinite_fluid_container", InfiniteFluidContainerBlock::new, containerProperties());

    public static final DeferredBlock<InfiniteChemicalContainerBlock> INFINITE_CHEMICAL_CONTAINER =
          BLOCKS.registerBlock("infinite_chemical_container", InfiniteChemicalContainerBlock::new, containerProperties());

    private static BlockBehaviour.Properties containerProperties() {
        return BlockBehaviour.Properties.of()
              .mapColor(DyeColor.BLACK)
              .strength(3.5F, 6.0F)
              .sound(SoundType.METAL)
              .requiresCorrectToolForDrops()
              .noOcclusion();
    }

    private BlockRegistry() {
    }
}
