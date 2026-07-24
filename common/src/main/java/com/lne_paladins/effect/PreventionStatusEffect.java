package com.lne_paladins.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;
import net.more_rpg_classes.client.particle.MoreParticles;
import net.more_rpg_classes.client.particle.PopupParticleEffect;
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
                popIcon(livingEntity);
            }
            livingEntity.removeStatusEffect(LNE_PaladinsEffects.PREVENTION.entry);
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
                popIcon(livingEntity);
            }
            livingEntity.removeStatusEffect(LNE_PaladinsEffects.PREVENTION.entry);
        }
        super.applyUpdateEffect(livingEntity, amplifier);
        return true;
    }

    private static void popIcon(LivingEntity livingEntity) {
        if (livingEntity.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(
                    new PopupParticleEffect(MoreParticles.POPUP, LNE_PaladinsEffects.PREVENTION.id, false, livingEntity.getId()),
                    livingEntity.getX(), livingEntity.getEyeY() + 0.2, livingEntity.getZ(),
                    1, 0, 0, 0, 0);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
