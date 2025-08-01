package com.asteristired.gastronomy.StatusEffects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.asteristired.gastronomy.Gastronomy.MOD_ID;

public class ModStatusEffects {
    public static final StatusEffect STUFFED = Registry.register(Registries.STATUS_EFFECT, new Identifier(MOD_ID, "stuffed"),
            new StuffedStatusEffect());

    public static void Initialise() {}
}
