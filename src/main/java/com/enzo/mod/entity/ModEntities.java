package com.enzo.mod.entity;

import com.enzo.mod.MyFabricMod;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<BuddhasFuryLotusEntity> BUDDHAS_FURY_LOTUS_TYPE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MyFabricMod.MOD_ID, "buddhas_fury_lotus"),
            FabricEntityTypeBuilder.<BuddhasFuryLotusEntity>create(SpawnGroup.MISC, (type, world) -> new BuddhasFuryLotusEntity(type, world))
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                    .build()
    );

    public static final EntityType<GuanyinTearEntity> GUANYIN_TEAR_ENTITY_TYPE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MyFabricMod.MOD_ID, "guanyin_tear_entity"),
            FabricEntityTypeBuilder.<GuanyinTearEntity>create(SpawnGroup.MISC, GuanyinTearEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build()
    );

    public static void registerEntities() {
        MyFabricMod.LOGGER.info("Registering Entities for " + MyFabricMod.MOD_ID);
    }
}
