package com.asteristired.gastronomy.client;

import com.asteristired.gastronomy.BlockEntities.ModBlockEntities;
import com.asteristired.gastronomy.Blocks.ModBlocks;
import com.asteristired.gastronomy.Gastronomy;
import com.asteristired.gastronomy.client.BlockEntityRenderer.CookingPotRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class GastronomyClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.COOKING_POT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.COOKING_POT, RenderLayer.getTranslucent());

        BlockEntityRendererFactories.register(ModBlockEntities.COOKING_POT, CookingPotRenderer::new);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            assert world != null;
            return BiomeColors.getWaterColor(world, pos);
        }, ModBlocks.COOKING_POT);
    }
}
