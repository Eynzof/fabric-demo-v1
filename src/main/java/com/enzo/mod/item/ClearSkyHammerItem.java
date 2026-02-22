package com.enzo.mod.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.UseAction;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;

public class ClearSkyHammerItem extends Item {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public ClearSkyHammerItem(Settings settings) {
        super(settings);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 160; // 8秒 = 160 ticks
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        // 播放开始格挡的音效
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.PLAYERS, 1.0f, 1.0f);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity player) {
            player.getItemCooldownManager().set(this, 60); // 3秒冷却 = 60 ticks
        }
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity player) {
            player.getItemCooldownManager().set(this, 60);
        }
        return super.finishUsing(stack, world, user);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        World world = target.getWorld();

        if (!world.isClient) {
            // 1. 造成 22 点基础伤害
            target.damage(world.getDamageSources().mobAttack(attacker), 22.0f);

            // 2. 快速升空 20 格 (使用速度和禁锢)
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 20, 20)); // 漂浮效果实现快速升空
            
            // 播放音效
            world.playSound(null, target.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1.0f, 0.5f);

            // 3. 延迟 1 秒后（约升空 20 格的时间）在空中爆炸并再掉 22 点血
            scheduler.schedule(() -> {
                if (target.isAlive() && target.getWorld() instanceof ServerWorld serverWorld) {
                    serverWorld.getServer().execute(() -> {
                        // 在生物当前位置创建爆炸特效
                        serverWorld.spawnParticles(ParticleTypes.EXPLOSION_EMITTER, target.getX(), target.getY(), target.getZ(), 1, 0, 0, 0, 0);
                        serverWorld.playSound(null, target.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 2.0f, 1.0f);
                        
                        // 再掉 22 点血
                        target.damage(serverWorld.getDamageSources().explosion(null, attacker), 22.0f);
                        
                        // 清除漂浮效果
                        target.removeStatusEffect(StatusEffects.LEVITATION);
                        
                        // 向四周施加一点冲击力
                        target.setVelocity(new Vec3d(0, -0.5, 0));
                    });
                }
            }, 1, TimeUnit.SECONDS);
        }

        return true;
    }
}
