package com.asteristired.gastronomy.mixin;

import com.asteristired.gastronomy.StatusEffects.ModStatusEffects;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public class HungerManagerMixin {

    @Shadow
    private float exhaustion;
    @Shadow private int foodLevel;

    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    private void slowHungerDrain(PlayerEntity player, CallbackInfo ci) {
        float requiredExhaustion = 4.0f;

        if (player.hasStatusEffect(ModStatusEffects.STUFFED)) {
            requiredExhaustion *= 2;
        }

        if (this.exhaustion >= requiredExhaustion) {
            this.exhaustion -= requiredExhaustion;
            if (this.foodLevel > 0) {
                this.foodLevel--;
            }
        }

        // Cancel original behavior
        ci.cancel();
    }
}