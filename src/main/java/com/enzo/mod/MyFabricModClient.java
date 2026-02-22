package com.enzo.mod;

import com.enzo.mod.screen.AlchemicalCrucibleScreen;
import com.enzo.mod.screen.ModScreenHandlers;
import com.enzo.mod.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class MyFabricModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.ALCHEMICAL_CRUCIBLE_SCREEN_HANDLER, AlchemicalCrucibleScreen::new);

        EntityRendererRegistry.register(ModEntities.BUDDHAS_FURY_LOTUS_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.GUANYIN_TEAR_ENTITY_TYPE, FlyingItemEntityRenderer::new);
    }
}
