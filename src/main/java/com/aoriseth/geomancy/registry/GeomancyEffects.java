package com.aoriseth.geomancy.registry;

import com.aoriseth.geomancy.Geomancy;
import com.aoriseth.geomancy.effects.ViaEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GeomancyEffects {

    public static final StatusEffect VIA = register("via_effect", new ViaEffect());

    public static void init(){
        // initialize status effects.
    }

    public static StatusEffect register(String name, StatusEffect effect) {
        return Registry.register(Registry.STATUS_EFFECT, new Identifier(Geomancy.MOD_ID, name ), effect);
    }
}
