package com.enzo.mod.entity;

import com.enzo.mod.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class BuddhasFuryLotusEntity extends Entity implements FlyingItemEntity {
    private static final TrackedData<ItemStack> ITEM = DataTracker.registerData(BuddhasFuryLotusEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
    private int fuse = 100; // 5秒 = 100 tick

    public BuddhasFuryLotusEntity(EntityType<? extends Entity> type, World world) {
        super(type, world);
    }

    public BuddhasFuryLotusEntity(World world, double x, double y, double z) {
        this(ModEntities.BUDDHAS_FURY_LOTUS_TYPE, world);
        this.setPosition(x, y, z);
        this.setStack(new ItemStack(ModItems.BUDDHAS_FURY_LOTUS));
        this.noClip = true; // 升空时穿过方块或保持稳定
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(ITEM, ItemStack.EMPTY);
    }

    public void setStack(ItemStack stack) {
        this.dataTracker.set(ITEM, stack);
    }

    @Override
    public ItemStack getStack() {
        return this.dataTracker.get(ITEM);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.getWorld().isClient) {
            // 缓慢升空
            this.setVelocity(0, 0.05, 0);
            this.move(net.minecraft.entity.MovementType.SELF, this.getVelocity());

            fuse--;
            if (fuse <= 0) {
                explode();
                this.discard();
            }
            
            // 粒子效果
            if (this.getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 2, 0.1, 0.1, 0.1, 0.01);
            }
        }
    }

    private void explode() {
        World world = this.getWorld();
        if (world.isClient) return;

        // 1. 范围伤害爆炸 (10点伤害)
        // 使用 createExplosion, 威力设为 3.0 左右（约等于 TNT 4.0 的程度，10点伤害可以通过控制威力或后续代码实现）
        // 实际上直接调用 damage 会更精准
        world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 3.0f, World.ExplosionSourceType.TNT);
        
        // 额外范围伤害确保 10 点
        world.getOtherEntities(this, this.getBoundingBox().expand(5.0)).forEach(entity -> {
            entity.damage(world.getDamageSources().explosion(null, null), 10.0f);
        });

        // 2. 生成烟雾特效
        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY(), this.getZ(), 100, 0.5, 0.5, 0.5, 0.1);
            serverWorld.spawnParticles(ParticleTypes.EXPLOSION_EMITTER, this.getX(), this.getY(), this.getZ(), 5, 0.5, 0.5, 0.5, 0.1);
        }

        // 3. 向四周发射 100 个 TNT
        for (int i = 0; i < 100; i++) {
            TntEntity tnt = new TntEntity(world, this.getX(), this.getY(), this.getZ(), null);
            tnt.setFuse(20 + world.random.nextInt(20)); // 设置 1-2 秒的随机引信，避免瞬间卡死
            
            // 随机方向
            double vx = (world.random.nextDouble() - 0.5) * 1.5;
            double vy = world.random.nextDouble() * 1.0;
            double vz = (world.random.nextDouble() - 0.5) * 1.5;
            tnt.setVelocity(vx, vy, vz);
            
            world.spawnEntity(tnt);
        }
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("Item", 10)) {
            this.setStack(ItemStack.fromNbt(nbt.getCompound("Item")));
        }
        this.fuse = nbt.getInt("Fuse");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.put("Item", this.getStack().writeNbt(new NbtCompound()));
        nbt.putInt("Fuse", this.fuse);
    }

    @Override
    public net.minecraft.network.packet.Packet<net.minecraft.network.listener.ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
