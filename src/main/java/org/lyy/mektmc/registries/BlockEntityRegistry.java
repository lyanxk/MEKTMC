package org.lyy.mektmc.registries;

import mekanism.common.capabilities.Capabilities;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.lyy.mektmc.Mektmc;
import org.lyy.mektmc.blockentity.InfiniteContentContainerBlockEntity;
import org.lyy.mektmc.blocks.InfiniteContentContainerBlock.ContainerType;

public final class BlockEntityRegistry {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
          DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Mektmc.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<InfiniteContentContainerBlockEntity>>
          INFINITE_CONTAINER = BLOCK_ENTITY_TYPES.register("infinite_container", () ->
                BlockEntityType.Builder.of(InfiniteContentContainerBlockEntity::new,
                      BlockRegistry.INFINITE_ITEM_CONTAINER.get(),
                      BlockRegistry.INFINITE_FLUID_CONTAINER.get(),
                      BlockRegistry.INFINITE_CHEMICAL_CONTAINER.get()).build(null));

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        BlockEntityType<InfiniteContentContainerBlockEntity> type = INFINITE_CONTAINER.get();
        event.registerBlockEntity(net.neoforged.neoforge.capabilities.Capabilities.ItemHandler.BLOCK, type,
              (blockEntity, side) -> blockEntity.isContainerType(ContainerType.ITEM)
                    ? blockEntity.getItemHandler()
                    : null);
        event.registerBlockEntity(net.neoforged.neoforge.capabilities.Capabilities.FluidHandler.BLOCK, type,
              (blockEntity, side) -> blockEntity.isContainerType(ContainerType.FLUID)
                    ? blockEntity.getFluidHandler()
                    : null);
        event.registerBlockEntity(Capabilities.CHEMICAL.block(), type,
              (blockEntity, side) -> blockEntity.isContainerType(ContainerType.CHEMICAL)
                    ? blockEntity.getChemicalHandler()
                    : null);
    }

    private BlockEntityRegistry() {
    }
}
