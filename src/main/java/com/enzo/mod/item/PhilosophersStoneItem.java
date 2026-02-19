package com.enzo.mod.item;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 贤者之石
 * 效果：右键铁块或铜块将其转换为金块
 */
public class PhilosophersStoneItem extends Item {
    public PhilosophersStoneItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        Block block = world.getBlockState(pos).getBlock();

        // 检查是否为铁块或铜块
        if (block == Blocks.IRON_BLOCK || block == Blocks.COPPER_BLOCK) {
            if (!world.isClient) {
                // 转换为金块
                world.setBlockState(pos, Blocks.GOLD_BLOCK.getDefaultState());

                // 播放声音
                world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
            return ActionResult.success(world.isClient);
        }

        return ActionResult.PASS;
    }
}
