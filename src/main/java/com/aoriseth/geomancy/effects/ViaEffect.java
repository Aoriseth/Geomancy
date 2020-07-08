package com.aoriseth.geomancy.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;

public class ViaEffect extends StatusEffect {

    public ViaEffect() {
        super(StatusEffectType.NEUTRAL, 0xFFFFFF);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        entity.applyStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60 * 20, 2, true, true));
    }
}
