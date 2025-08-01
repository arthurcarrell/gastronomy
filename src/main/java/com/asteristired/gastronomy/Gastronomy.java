package com.asteristired.gastronomy;

import com.asteristired.gastronomy.BlockEntities.ModBlockEntities;
import com.asteristired.gastronomy.Blocks.ModBlocks;
import com.asteristired.gastronomy.Items.ModItems;
import com.asteristired.gastronomy.StatusEffects.ModStatusEffects;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class Gastronomy implements ModInitializer {

    public static final String MOD_ID = "gastronomy";

    public static int debugOffset = 91;
    @Override
    public void onInitialize() {
        ModBlockEntities.Initalise();
        ModBlocks.Initalise();
        ModItems.Initalise();
        ModStatusEffects.Initialise();
    }
}
