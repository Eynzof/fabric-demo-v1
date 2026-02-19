package com.enzo.mod.screen;

import com.enzo.mod.MyFabricMod;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<AlchemicalCrucibleScreenHandler> ALCHEMICAL_CRUCIBLE_SCREEN_HANDLER = Registry
            .register(Registries.SCREEN_HANDLER, new Identifier(MyFabricMod.MOD_ID, "alchemical_crucible"),
                    new ExtendedScreenHandlerType<>(AlchemicalCrucibleScreenHandler::new));

    public static void registerScreenHandlers() {
        MyFabricMod.LOGGER.info("Registering Screen Handlers for " + MyFabricMod.MOD_ID);
    }
}
