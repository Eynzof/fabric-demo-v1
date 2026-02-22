package com.enzo.mod.mixin;

import com.enzo.mod.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class ClearSkyHammerMixin {
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void protectWhileBlocking(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        // 检查实体是否正在使用昊天锤进行格挡
        if (entity.isUsingItem() && entity.getActiveItem().isOf(ModItems.CLEAR_SKY_HAMMER)) {
            // 抵御一切伤害
            cir.setReturnValue(false);
        }
    }
}
