package com.lne_paladins.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.spell_engine.api.spell.fx.ParticleBatch;
import net.spell_engine.client.util.Color;
import net.spell_engine.fx.ParticleHelper;

public class PreventionStatusEffect extends StatusEffect {
    protected PreventionStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    private static final ParticleBatch particles = new ParticleBatch(
            "spell_engine:magic_heal_ascend",
            ParticleBatch.Shape.WIDE_PIPE,
            ParticleBatch.Origin.GROUND,
            null,
            25,
            0.02F,
            0.15F,
            0,
            0.5F).color(Color.HOLY.toRGBA());
    private static final ParticleBatch particles1 = new ParticleBatch(
            "spell_engine:healing_ascend",
            ParticleBatch.Shape.PILLAR,
            ParticleBatch.Origin.FEET,
            null,
            20,
            0.02F,
            0.15F,
            0,
            0.5F);

    public void onApplied(LivingEntity livingEntity, int amplifier) {
        super.onApplied(livingEntity, amplifier);
        float actual_health_percentage = livingEntity.getHealth() / livingEntity.getMaxHealth();
        float heal_amount = livingEntity.getMaxHealth() * (0.05F + (0.05F + amplifier));
        if(actual_health_percentage <= 0.2F){
            livingEntity.heal(heal_amount);
            if(!livingEntity.getWorld().isClient()){
                ParticleHelper.sendBatches(livingEntity, new ParticleBatch[]{particles});
                ParticleHelper.sendBatches(livingEntity, new ParticleBatch[]{particles1});
            }
            livingEntity.removeStatusEffect(Effects.PREVENTION.registryEntry);
        }
    }

    public boolean applyUpdateEffect(LivingEntity livingEntity, int amplifier) {
        float actual_health_percentage = livingEntity.getHealth() / livingEntity.getMaxHealth();
        float heal_amount = livingEntity.getMaxHealth() * (0.3F + (0.1F * amplifier));
        if(actual_health_percentage <= 0.2F){
            livingEntity.heal(heal_amount);
            if(!livingEntity.getWorld().isClient()){
                ParticleHelper.sendBatches(livingEntity, new ParticleBatch[]{particles});
                ParticleHelper.sendBatches(livingEntity, new ParticleBatch[]{particles1});
            }
            livingEntity.removeStatusEffect(Effects.PREVENTION.registryEntry);
        }
        super.applyUpdateEffect(livingEntity, amplifier);
        return true;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
