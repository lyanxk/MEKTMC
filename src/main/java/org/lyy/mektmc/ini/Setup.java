package org.lyy.mektmc.ini;

import appeng.api.storage.StorageCells;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.lyy.mektmc.ae.InfiniteCellHandler;

public final class Setup {
    public static void onCommonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            StorageCells.addCellHandler(InfiniteCellHandler.INSTANCE);
        });
    }

    private Setup() {}
}
