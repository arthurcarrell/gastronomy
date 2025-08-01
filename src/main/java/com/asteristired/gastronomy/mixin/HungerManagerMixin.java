package com.asteristired.gastronomy.mixin;

import com.asteristired.gastronomy.StatusEffects.ModStatusEffects;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public abstract class HungerManagerMixin {

    @Shadow
    private float exhaustion;
    @Shadow private int foodLevel;

    @Shadow private float saturationLevel;

    @Shadow private int foodTickTimer;

    @Shadow public abstract void addExhaustion(float exhaustion);

    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    private void slowHungerDrain(PlayerEntity player, CallbackInfo ci) {
        float requiredExhaustion = 4.0f;

        if (player.hasStatusEffect(ModStatusEffects.STUFFED)) {
            requiredExhaustion *= 2;
        }

        Difficulty difficulty = player.getWorld().getDifficulty();

        // This is all code that has been ripped from "update" in HungerManager by mojang
        // this is why the variable names are disgusting, as they are obfuscated.
        if (this.exhaustion > 4.0F) {
            this.exhaustion -= 4.0F;
            if (this.saturationLevel > 0.0F) {
                this.saturationLevel = Math.max(this.saturationLevel - 1.0F, 0.0F);
            } else if (difficulty != Difficulty.PEACEFUL) {
                this.foodLevel = Math.max(this.foodLevel - 1, 0);
            }
        }

        boolean bl = player.getWorld().getGameRules().getBoolean(GameRules.NATURAL_REGENERATION);

        if (bl && this.saturationLevel > 0.0F && player.canFoodHeal() && this.foodLevel >= 20) {
            ++this.foodTickTimer;
            if (this.foodTickTimer >= 10) {
                float f = Math.min(this.saturationLevel, 6.0F);
                player.heal(f / 6.0F);
                this.addExhaustion(f);
                this.foodTickTimer = 0;
            }
        } else if (bl && this.foodLevel >= 18 && player.canFoodHeal()) {
            ++this.foodTickTimer;
            if (this.foodTickTimer >= 80) {
                player.heal(1.0F);
                this.addExhaustion(6.0F);
                this.foodTickTimer = 0;
            }
        } else if (this.foodLevel <= 0) {
            ++this.foodTickTimer;
            if (this.foodTickTimer >= 80) {
                if (player.getHealth() > 10.0F || difficulty == Difficulty.HARD || player.getHealth() > 1.0F && difficulty == Difficulty.NORMAL) {
                    player.damage(player.getDamageSources().starve(), 1.0F);
                }

                this.foodTickTimer = 0;
            }
        } else {
            this.foodTickTimer = 0;
        }

        // The HungerDrain has been completely recreated by me, and so the original function should not be called.
        ci.cancel();
    }
}