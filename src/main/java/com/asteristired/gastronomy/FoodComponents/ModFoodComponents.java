package com.asteristired.gastronomy.FoodComponents;

import net.minecraft.item.FoodComponent;

public class ModFoodComponents {

    public static final FoodComponent EMPTY_FOOD_COMPONENT = new FoodComponent.Builder()
            .hunger(0)
            .saturationModifier(0F)
            .build();
    public void Initalise() {}
}
