package com.asteristired.gastronomy.Tags;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

import static com.asteristired.gastronomy.Gastronomy.MOD_ID;

public class ModTags {
    public static final TagKey<Block> COOKING_POT_WARMERS = TagKey.of(RegistryKeys.BLOCK, new Identifier(MOD_ID, "cooking_pot_warmers"));

    public static final TagKey<Item> CAN_BE_PLACED_IN_POT = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "can_be_placed_in_pot"));
    public static final TagKey<Item> STUFFED = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "stuffed"));
    public static final TagKey<Item> FRUIT_AND_VEGETABLES = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "fruit_and_vegetables"));
    public static final TagKey<Item> FISH = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "fish"));
    public static final TagKey<Item> GOLDEN_FOOD = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "golden_food"));
    public static final TagKey<Item> MEAT = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "meat"));
    public static final TagKey<Item> SUGAR = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "sugar"));

    public static void Initalise() {}
}

