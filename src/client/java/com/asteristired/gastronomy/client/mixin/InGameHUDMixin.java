package com.asteristired.gastronomy.client.mixin;

import com.asteristired.gastronomy.Gastronomy;
import com.asteristired.gastronomy.StatusEffects.ModStatusEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHUDMixin {

    @Redirect(
            method = "renderStatusBars",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"
            )
    )
    private void renderStatusBarsWithGold(DrawContext instance, Identifier texture, int x, int y, int u, int v, int width, int height) {
        assert MinecraftClient.getInstance().player != null;
        boolean drawGold = MinecraftClient.getInstance().player.hasStatusEffect(ModStatusEffects.STUFFED);
        if (texture.equals(Identifier.of("minecraft", "textures/gui/icons.png")) && drawGold) {
            texture = Identifier.of("gastronomy", "textures/gui/icons_gold.png");
        }

        instance.drawTexture(texture, x, y, u, v, width, height);
    }
}