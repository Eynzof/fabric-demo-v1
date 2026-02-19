package com.enzo.mod.block;

import com.enzo.mod.MyFabricMod;
import com.enzo.mod.item.ModItems;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block ALCHEMICAL_CRUCIBLE = registerBlock("alchemical_crucible",
            new AlchemicalCrucibleBlock(FabricBlockSettings.copyOf(Blocks.STONE).nonOpaque()));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(MyFabricMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(MyFabricMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        MyFabricMod.LOGGER.info("Registering Mod Blocks for " + MyFabricMod.MOD_ID);
    }
}
