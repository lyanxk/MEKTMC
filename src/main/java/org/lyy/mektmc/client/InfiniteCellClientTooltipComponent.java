package org.lyy.mektmc.client;

import appeng.api.client.AEKeyRendering;
import appeng.api.stacks.AEKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import org.lyy.mektmc.ae.InfiniteCellTooltipComponent;

public final class InfiniteCellClientTooltipComponent implements ClientTooltipComponent {

    private static final int MAX_COLUMNS = 8;
    private static final int SLOT_SIZE = 18;
    private static final Component INFINITY = Component.literal("∞");

    private final InfiniteCellTooltipComponent component;

    public InfiniteCellClientTooltipComponent(InfiniteCellTooltipComponent component) {
        this.component = component;
    }

    @Override
    public int getHeight() {
        return rows() * SLOT_SIZE;
    }

    @Override
    public int getWidth(Font font) {
        return columns() * SLOT_SIZE;
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics graphics) {
        Minecraft minecraft = Minecraft.getInstance();
        for (int index = 0; index < component.keys().size(); index++) {
            AEKey key = component.keys().get(index);
            int iconX = x + index % MAX_COLUMNS * SLOT_SIZE;
            int iconY = y + index / MAX_COLUMNS * SLOT_SIZE;
            AEKeyRendering.drawInGui(minecraft, graphics, iconX, iconY, key);
            int labelX = iconX + 17 - font.width(INFINITY);
            int labelY = iconY + 8;
            graphics.pose().pushPose();
            graphics.pose().translate(0, 0, 200);
            graphics.drawString(font, INFINITY, labelX, labelY, 0xFFFFFF, true);
            graphics.pose().popPose();
        }
    }

    private int columns() {
        return Math.min(MAX_COLUMNS, component.keys().size());
    }

    private int rows() {
        return (component.keys().size() + MAX_COLUMNS - 1) / MAX_COLUMNS;
    }
}
