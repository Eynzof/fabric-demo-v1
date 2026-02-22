package com.enzo.mod.item;

import com.enzo.mod.entity.BuddhasFuryLotusEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

/**
 * 佛怒唐莲
 * 功能：右键敌人时，在敌人脚下生成一个缓慢升空的佛怒唐莲，5秒后爆炸。
 */
public class BuddhasFuryLotusItem extends Item {
    public BuddhasFuryLotusItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        World world = user.getWorld();

        if (!world.isClient) {
            // 在目标敌人位置生成佛怒唐莲实体
            BuddhasFuryLotusEntity lotus = new BuddhasFuryLotusEntity(world, entity.getX(), entity.getY(), entity.getZ());
            world.spawnEntity(lotus);

            // 消耗物品
            if (!user.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }

        return ActionResult.success(world.isClient);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // 如果没有触发 useOnEntity (即没点到生物)，且不是客户端，提示玩家
        if (!world.isClient) {
            // 注意：因为 useOnEntity 优先触发，如果能到这里说明没点到生物
            user.sendMessage(Text.literal("请指定使用目标"), true);
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }
}
