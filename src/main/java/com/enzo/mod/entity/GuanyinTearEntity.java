package com.enzo.mod.entity;

import com.enzo.mod.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class GuanyinTearEntity extends ThrownItemEntity {
    public GuanyinTearEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public GuanyinTearEntity(World world, LivingEntity owner) {
        super(ModEntities.GUANYIN_TEAR_ENTITY_TYPE, owner, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.GUANYIN_TEAR;
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            World world = this.getWorld();
            
            // 产生巨大爆炸 (威力10)
            world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 10.0f, true, World.ExplosionSourceType.TNT);

            // 1000 点伤害极其夸张，通常会导致目标直接消失
            // 我们对半径 10 格内的生物造成伤害
            world.getOtherEntities(this, this.getBoundingBox().expand(10.0)).forEach(entity -> {
                entity.damage(world.getDamageSources().explosion(this, this.getOwner()), 1000.0f);
            });

            // 如果直接撞到了生物，确保它死透了
            if (hitResult.getType() == HitResult.Type.ENTITY) {
                Entity hitEntity = ((EntityHitResult) hitResult).getEntity();
                hitEntity.damage(world.getDamageSources().explosion(this, this.getOwner()), 1000.0f);
            }

            // 粒子效果
            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(ParticleTypes.FLASH, this.getX(), this.getY(), this.getZ(), 20, 0.5, 0.5, 0.5, 0.1);
            }

            this.discard();
        }
    }

    @Override
    public void tick() {
        super.tick();
        // 添加高速轨迹粒子
        if (this.getWorld().isClient) {
            this.getWorld().addParticle(ParticleTypes.END_ROD, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        }
    }
}
