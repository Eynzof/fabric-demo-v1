package com.enzo.mod.item;

import com.enzo.mod.MyFabricMod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    // 注册物品
    public static final Item PHILOSOPHERS_STONE = registerItem("philosophers_stone",
            new PhilosophersStoneItem(new FabricItemSettings().maxCount(1)));

    public static final Item MERCURY = registerItem("mercury",
            new Item(new FabricItemSettings()));

    public static final Item LOTUS_CORE = registerItem("lotus_core",
            new Item(new FabricItemSettings().rarity(net.minecraft.util.Rarity.EPIC)));

    public static final Item LAUNCH_MECHANISM = registerItem("launch_mechanism",
            new Item(new FabricItemSettings()));

    public static final Item DAMAGE_CORE = registerItem("damage_core",
            new Item(new FabricItemSettings().rarity(net.minecraft.util.Rarity.RARE)));

    public static final Item HAMMER_CORE = registerItem("hammer_core",
            new Item(new FabricItemSettings().rarity(net.minecraft.util.Rarity.RARE)));

    public static final Item CLEAR_SKY_HAMMER = registerItem("clear_sky_hammer",
            new ClearSkyHammerItem(new FabricItemSettings().rarity(net.minecraft.util.Rarity.EPIC).maxCount(1)));

    public static final Item BUDDHAS_FURY_LOTUS = registerItem("buddhas_fury_lotus",
            new BuddhasFuryLotusItem(new FabricItemSettings().rarity(net.minecraft.util.Rarity.EPIC).maxCount(1)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MyFabricMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MyFabricMod.LOGGER.info("Registering Mod Items for " + MyFabricMod.MOD_ID);

        // 将物品添加到创造模式物品栏（工具栏）
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.add(PHILOSOPHERS_STONE);
            content.add(BUDDHAS_FURY_LOTUS);
        });

        // 将物品添加到创造模式物品栏（原材料）
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> {
            content.add(MERCURY);
            content.add(LOTUS_CORE);
            content.add(DAMAGE_CORE);
            content.add(HAMMER_CORE);
        });

        // 将炼金锅添加到创造模式物品栏（功能方块）
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content -> {
            content.add(com.enzo.mod.block.ModBlocks.ALCHEMICAL_CRUCIBLE);
            content.add(LAUNCH_MECHANISM);
        });

        // 将昊天锤添加到工具栏
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
            content.add(CLEAR_SKY_HAMMER);
        });
    }
}
