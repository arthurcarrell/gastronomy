package com.asteristired.gastronomy.Items;

import com.asteristired.gastronomy.FoodComponents.ModFoodComponents;
import com.asteristired.gastronomy.Items.ItemClasses.CookedFood;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import static com.asteristired.gastronomy.Gastronomy.MOD_ID;

public class ModItems {



    public static Item COOKED_FOOD = Register(new CookedFood(new Item.Settings().food(ModFoodComponents.EMPTY_FOOD_COMPONENT)
    ), "cooked_food"); // requires a food component in order to be classified as food.
    // the food component is empty as the amount of hunger and saturation to restore is dynamic.


    // custom registry system (https://docs.fabricmc.net/1.20.4/develop/items/first-item)
    public static Item Register(Item item, String id) {
        // create the identifier
        Identifier ID = new Identifier(MOD_ID, id);

        // register the item
        Item registeredItem = Registry.register(Registries.ITEM, ID, item);

        // return it
        return registeredItem;
    }

    public static void AddItemsToGroups() {

    }


    public static void Initalise() {
        return;
    }
}