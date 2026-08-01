package org.lyy.mektmc.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.lyy.mektmc.Mektmc;
import org.lyy.mektmc.registries.BlockEntityRegistry;

@EventBusSubscriber(modid = Mektmc.MODID, value = Dist.CLIENT)
public final class ClientRegistration {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityRegistry.INFINITE_CONTAINER.get(),
              InfiniteContentContainerRenderer::new);
    }

    private ClientRegistration() {
    }
}
