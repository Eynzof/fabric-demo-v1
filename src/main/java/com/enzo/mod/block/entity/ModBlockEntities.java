package com.enzo.mod.block.entity;

import com.enzo.mod.MyFabricMod;
import com.enzo.mod.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<AlchemicalCrucibleBlockEntity> ALCHEMICAL_CRUCIBLE_BE = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(MyFabricMod.MOD_ID, "alchemical_crucible_be"),
            FabricBlockEntityTypeBuilder.create(AlchemicalCrucibleBlockEntity::new,
                    ModBlocks.ALCHEMICAL_CRUCIBLE).build());

    public static void registerBlockEntities() {
        MyFabricMod.LOGGER.info("Registering Block Entities for " + MyFabricMod.MOD_ID);
    }
}
