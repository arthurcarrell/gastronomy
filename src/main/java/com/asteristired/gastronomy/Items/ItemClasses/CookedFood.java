package com.asteristired.gastronomy.Items.ItemClasses;

import com.asteristired.gastronomy.StatusEffects.ModStatusEffects;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtInt;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CookedFood extends Item {
    public CookedFood(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        // usually, you create a FoodComponent and then assign it to an item, but because this is dynamic
        // we have to implement that ourselves.

        // get NBT data
        NbtCompound nbt = stack.getOrCreateNbt();
        int hunger = nbt.getInt("hunger");
        float saturation = nbt.getFloat("saturation");

        if (user instanceof PlayerEntity player) {
            player.getHungerManager().add(hunger, saturation);
        }

        if (nbt.getInt("bonus_hearts") > 0) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 1200, nbt.getInt("bonus_hearts")));
        }

        if (nbt.getBoolean("stuffed")) {
            user.addStatusEffect(new StatusEffectInstance(ModStatusEffects.STUFFED, 2400) );
        }

        return super.finishUsing(stack, world, user);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbt = stack.getOrCreateNbt();

        int bonus_hearts = nbt.getInt("bonus_hearts");
        boolean isStuffedBuff = nbt.getBoolean("stuffed");

        if (bonus_hearts > 0) {
            tooltip.add(Text.literal("♥ x " + bonus_hearts).formatted(Formatting.YELLOW));
        }
        if (isStuffedBuff) {
            tooltip.add(Text.literal("\uD83E\uDD55+").formatted(Formatting.YELLOW));
        }

        tooltip.add(Text.literal("\uD83C\uDF56 x " + nbt.getInt("hunger")).formatted(Formatting.RED));
        tooltip.add(Text.literal("♦ x " + nbt.getFloat("saturation")).formatted(Formatting.GOLD));

        super.appendTooltip(stack, world, tooltip, context);
    }
}
