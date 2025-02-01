package com.lne_paladins.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.spell_engine.api.spell.ParticleBatch;
import net.spell_engine.particle.ParticleHelper;

public class PreventionStatusEffect extends StatusEffect {
    protected PreventionStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    private static final ParticleBatch particles = new ParticleBatch(
            "lne_paladins:prevention_sign",
            ParticleBatch.Shape.PIPE,
            ParticleBatch.Origin.CENTER,
            null,
            1,
            0.55F,
            0.55F,
            0,
            -0.5F);
    private static final ParticleBatch particles1 = new ParticleBatch(
            "spell_engine:healing_ascend",
            ParticleBatch.Shape.PILLAR,
            ParticleBatch.Origin.FEET,
            null,
            20,
            0.02F,
            0.15F,
            0,
            1);

    @Override
    public void onApplied(LivingEntity livingEntity, AttributeContainer attributes, int amplifier) {
        float actual_health_percentage = livingEntity.getHealth() / livingEntity.getMaxHealth();
        float heal_amount = livingEntity.getMaxHealth() * (0.05F + (0.05F + amplifier));
        if(actual_health_percentage <= 0.2F){
            livingEntity.heal(heal_amount);
            if(!livingEntity.getWorld().isClient()){
                ParticleHelper.sendBatches(livingEntity, new ParticleBatch[]{particles});
                ParticleHelper.sendBatches(livingEntity, new ParticleBatch[]{particles1});
            }
            livingEntity.removeStatusEffect(Effects.PREVENTION);
        }
    }

    @Override
    public void applyUpdateEffect(LivingEntity livingEntity, int pAmplifier) {
        float actual_health_percentage = livingEntity.getHealth() / livingEntity.getMaxHealth();
        float heal_amount = livingEntity.getMaxHealth() * (0.3F + (0.1F * pAmplifier));
        if(actual_health_percentage <= 0.2F){
            livingEntity.heal(heal_amount);
            if(!livingEntity.getWorld().isClient()){
                ParticleHelper.sendBatches(livingEntity, new ParticleBatch[]{particles});
                ParticleHelper.sendBatches(livingEntity, new ParticleBatch[]{particles1});
            }
            livingEntity.removeStatusEffect(Effects.PREVENTION);
        }
        super.applyUpdateEffect(livingEntity, pAmplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
