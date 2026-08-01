package org.lyy.mektmc.client;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket.Action;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.lyy.mektmc.Mektmc;
import org.lyy.mektmc.blockentity.InfiniteContentContainerBlockEntity;
import org.lyy.mektmc.blocks.InfiniteItemContainerBlock;

@EventBusSubscriber(modid = Mektmc.MODID, value = Dist.CLIENT)
public final class ClientItemContainerInteractionTracker {

    private static BlockPos activePos;
    private static Direction activeFace;

    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (!(event.getLevel().getBlockState(event.getPos()).getBlock() instanceof InfiniteItemContainerBlock)
              || !(event.getLevel().getBlockEntity(event.getPos()) instanceof InfiniteContentContainerBlockEntity blockEntity)
              || blockEntity.getRenderItem().isEmpty()) {
            return;
        }

        event.setCanceled(true);
        if (event.getAction() != PlayerInteractEvent.LeftClickBlock.Action.START) {
            return;
        }

        Direction clickedFace = event.getFace();
        if (clickedFace == null) {
            clickedFace = event.getEntity().getDirection().getOpposite();
        }
        if (activePos != null && !activePos.equals(event.getPos())) {
            sendAbortPacket(Minecraft.getInstance());
        }
        activePos = event.getPos().immutable();
        activeFace = clickedFace;
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (activePos != null && !minecraft.options.keyAttack.isDown()) {
            sendAbortPacket(minecraft);
        }
    }

    private static void sendAbortPacket(Minecraft minecraft) {
        if (activePos != null && activeFace != null && minecraft.getConnection() != null) {
            minecraft.getConnection().send(
                  new ServerboundPlayerActionPacket(Action.ABORT_DESTROY_BLOCK, activePos, activeFace));
        }
        activePos = null;
        activeFace = null;
    }

    private ClientItemContainerInteractionTracker() {
    }
}
