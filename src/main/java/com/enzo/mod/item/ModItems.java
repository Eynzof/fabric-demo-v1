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

    // 注册贤者之石
    public static final Item PHILOSOPHERS_STONE = registerItem("philosophers_stone",
            new PhilosophersStoneItem(new FabricItemSettings().maxCount(1)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MyFabricMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MyFabricMod.LOGGER.info("Registering Mod Items for " + MyFabricMod.MOD_ID);

        // 将物品添加到创造模式物品栏（工具栏）
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.add(PHILOSOPHERS_STONE);
        });
    }
}
