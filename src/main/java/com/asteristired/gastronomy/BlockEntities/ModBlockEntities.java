package com.asteristired.gastronomy.BlockEntities;

import com.asteristired.gastronomy.Blocks.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import static com.asteristired.gastronomy.Gastronomy.MOD_ID;

public class ModBlockEntities {
    public static BlockEntityType<CookingPotBlockEntity> COOKING_POT = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(MOD_ID, "cooking_pot"),
            FabricBlockEntityTypeBuilder.create(
                    CookingPotBlockEntity::new,
                    ModBlocks.COOKING_POT
            ).build()
    );

    public static void Initalise() {}
}
