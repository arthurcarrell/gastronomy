package com.asteristired.gastronomy.Blocks;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import static com.asteristired.gastronomy.Gastronomy.MOD_ID;

public class ModBlocks {
    public static Block COOKING_POT = Register(new CookingPot(AbstractBlock.Settings.create().nonOpaque().strength(0.5f)), "cooking_pot");
    public static BlockItem COOKING_POT_ITEM = Register(new BlockItem(COOKING_POT, new Item.Settings().rarity(Rarity.UNCOMMON)), "cooking_pot");

    public static Block Register(Block block, String id) {
        return Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, id), block);
    }

    public static BlockItem Register(BlockItem block, String id) {
        return Registry.register(Registries.ITEM, Identifier.of(MOD_ID, id), block);
    }

    public static void AddToCreativeMenu() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(itemGroup -> {
//            itemGroup.addAfter(Items.PINK_CANDLE, ModBlocks.CALMING_CANDLE_ITEM);
//            itemGroup.addAfter(ModBlocks.CALMING_CANDLE_ITEM, ModBlocks.RAGING_CANDLE_ITEM);
        });
    }

    public static void Initalise() {}

}
