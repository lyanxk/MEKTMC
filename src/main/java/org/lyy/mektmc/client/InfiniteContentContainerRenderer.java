package org.lyy.mektmc.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mekanism.api.chemical.ChemicalStack;
import mekanism.client.render.MekanismRenderer;
import mekanism.client.render.MekanismRenderer.FluidTextureType;
import mekanism.client.render.MekanismRenderer.Model3D;
import mekanism.client.render.RenderResizableCuboid.FaceDisplay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import org.lyy.mektmc.blockentity.InfiniteContentContainerBlockEntity;

public final class InfiniteContentContainerRenderer
      implements BlockEntityRenderer<InfiniteContentContainerBlockEntity> {

    private static final float CONTENT_MIN = 1.001F / 16.0F;
    private static final float CONTENT_MAX = 14.999F / 16.0F;
    private static final int ITEM_ROTATION_TICKS = 5 * 20;
    private static final double ITEM_RENDER_Y = 0.38D;
    private static final float ITEM_RENDER_SCALE = 1.1F;

    private final ItemRenderer itemRenderer;

    public InfiniteContentContainerRenderer(BlockEntityRendererProvider.Context context) {
        itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(InfiniteContentContainerBlockEntity blockEntity, float partialTick, PoseStack poseStack,
          MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemStack item = blockEntity.getRenderItem();
        if (!item.isEmpty()) {
            renderItem(blockEntity, item, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
            return;
        }

        FluidStack fluid = blockEntity.getRenderFluid();
        if (!fluid.isEmpty()) {
            TextureAtlasSprite sprite = MekanismRenderer.getFluidTexture(fluid, FluidTextureType.STILL);
            int color = MekanismRenderer.getColorARGB(fluid);
            int light = MekanismRenderer.calculateGlowLight(packedLight, fluid);
            renderContent(blockEntity, poseStack, bufferSource, packedOverlay, sprite, color, light);
            return;
        }

        ChemicalStack chemical = blockEntity.getRenderChemical();
        if (!chemical.isEmpty()) {
            TextureAtlasSprite sprite = MekanismRenderer.getChemicalTexture(chemical);
            int color = MekanismRenderer.getColorARGB(chemical, 1.0F);
            renderContent(blockEntity, poseStack, bufferSource, packedOverlay, sprite, color, packedLight);
        }
    }

    private void renderItem(InfiniteContentContainerBlockEntity blockEntity, ItemStack item, float partialTick,
          PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Level level = blockEntity.getLevel();
        if (level == null) {
            return;
        }

        float rotation = ((level.getGameTime() % ITEM_ROTATION_TICKS) + partialTick)
              * (360.0F / ITEM_ROTATION_TICKS);
        poseStack.pushPose();
        poseStack.translate(0.5D, ITEM_RENDER_Y, 0.5D);
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
        poseStack.scale(ITEM_RENDER_SCALE, ITEM_RENDER_SCALE, ITEM_RENDER_SCALE);
        itemRenderer.renderStatic(item, ItemDisplayContext.GROUND, packedLight, packedOverlay,
              poseStack, bufferSource, level, (int) blockEntity.getBlockPos().asLong());
        poseStack.popPose();
    }

    private static void renderContent(InfiniteContentContainerBlockEntity blockEntity, PoseStack poseStack,
          MultiBufferSource bufferSource, int packedOverlay, TextureAtlasSprite sprite, int color, int packedLight) {
        Model3D model = new Model3D()
              .setTexture(sprite)
              .bounds(CONTENT_MIN, CONTENT_MAX);
        MekanismRenderer.renderObject(model, poseStack,
              bufferSource.getBuffer(Sheets.translucentCullBlockSheet()), color, packedLight, packedOverlay,
              FaceDisplay.FRONT, Minecraft.getInstance().gameRenderer.getMainCamera(), blockEntity.getBlockPos());
    }
}
