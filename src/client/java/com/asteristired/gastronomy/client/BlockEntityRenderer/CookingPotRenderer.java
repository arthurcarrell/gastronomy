package com.asteristired.gastronomy.client.BlockEntityRenderer;

import com.asteristired.gastronomy.BlockEntities.CookingPotBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.RotationAxis;

public class CookingPotRenderer implements BlockEntityRenderer<CookingPotBlockEntity> {
    public CookingPotRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(CookingPotBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        // get items in the pot
        DefaultedList<ItemStack> items = entity.getItems();
        MinecraftClient client = MinecraftClient.getInstance();

        for (int i = 0; i < items.size(); i++) {
            ItemStack stack = items.get(i);
            if (!stack.isEmpty()) {
                matrices.push();

                float X;
                float Y;
                float Z;
                if (i % 2 == 0) {
                    X = 0.35f;
                } else {
                    X = 0.65f;
                }

                if (i == 0 || i == 1) {
                    Z = 0.3f;
                } else {
                    Z = 0.6f;
                }

                matrices.translate(X, 0.35f, Z);
                matrices.scale(0.6f, 0.6f, 0.6f);
                matrices.multiply(RotationAxis.POSITIVE_X.rotation(1.570796f));

                client.getItemRenderer().renderItem(stack, ModelTransformationMode.GROUND, light, overlay, matrices, vertexConsumers, null, 0);

                matrices.pop();
            }
        }
    }
}
