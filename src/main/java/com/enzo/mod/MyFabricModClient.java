package com.enzo.mod;

import com.enzo.mod.screen.AlchemicalCrucibleScreen;
import com.enzo.mod.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class MyFabricModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.ALCHEMICAL_CRUCIBLE_SCREEN_HANDLER, AlchemicalCrucibleScreen::new);
    }
}
