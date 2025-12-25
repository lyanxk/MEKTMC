package org.lyy.mektmc.ini;

import appeng.api.storage.StorageCells;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.lyy.mektmc.ae.InfiniteGasCellHandler;

public final class Setup {
    public static void onCommonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            StorageCells.addCellHandler(InfiniteGasCellHandler.INSTANCE);
        });
    }

    private Setup() {}
}
