package com.enzo.mod.item;

import com.enzo.mod.entity.GuanyinTearEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class GuanyinTearItem extends Item {
    public GuanyinTearItem(Settings settings) {
        super(settings);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW; // 蓄力动作
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 100; // 5秒 = 100 ticks
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return TypedActionResult.consume(user.getStackInHand(hand));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity player) {
            if (!world.isClient) {
                // 1. 扣除一颗心 (2点生命)
                player.damage(world.getDamageSources().magic(), 2.0f);

                // 2. 发射投掷物
                GuanyinTearEntity tear = new GuanyinTearEntity(world, player);
                tear.setVelocity(player, player.getPitch(), player.getYaw(), 0.0f, 4.0f, 0.1f); // 高速
                world.spawnEntity(tear);

                // 3. 消耗物品
                if (!player.getAbilities().creativeMode) {
                    stack.decrement(1);
                }
                
                // 播放音效
                world.playSound(null, player.getX(), player.getY(), player.getZ(), 
                    SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0f, 0.2f);
            }
        }
        return stack;
    }
}
