package com.lne_paladins.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;
import net.more_rpg_classes.client.particle.MoreParticles;
import net.more_rpg_classes.client.particle.PopupParticleEffect;
import net.spell_engine.api.spell.fx.ParticleGroup;
import net.spell_engine.api.spell.fx.ParticleGroupBuilder;
import net.spell_engine.client.util.Color;
import net.spell_engine.fx.ParticleHelper;
import net.spell_engine.fx.SpellEngineParticles;

import java.util.List;

public class PreventionStatusEffect extends StatusEffect {
    protected PreventionStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    // V1: `magic_heal_ascend`, WIDE_PIPE (= PIPE at double the body radius), GROUND anchored.
    private static final ParticleGroup particles =
            ParticleGroupBuilder.magic(SpellEngineParticles.magic_heal, ParticleGroup.Motion.ASCEND, Color.HOLY)
                    .batch(b -> b.shape(ParticleGroup.Shape.PIPE).widthFactor(2F)
                            .anchor(ParticleGroup.Anchor.GROUND)
                            .count(25F).speed(0.02F, 0.15F)
                            .extent(0.5F));
    // V1 named `spell_engine:healing_ascend`, which was never a registered particle type: V1 magic ids
    // are `magic_<shape>_<float|ascend|decelerate|burst>` and there is no `healing` shape. This batch has
    // therefore rendered nothing in every shipped version. Ported to the effect it plainly meant - the
    // healing magic particle, ASCEND motion, untinted as authored - which makes it start rendering.
    private static final ParticleGroup particles1 =
            ParticleGroupBuilder.magic(SpellEngineParticles.magic_heal, ParticleGroup.Motion.ASCEND)
                    .batch(b -> b.shape(ParticleGroup.Shape.PILLAR)
                            .count(20F).speed(0.02F, 0.15F)
                            .verticalOrigin(ParticleGroupBuilder.Batches.FEET)
                            .extent(0.5F));

    public void onApplied(LivingEntity livingEntity, int amplifier) {
        super.onApplied(livingEntity, amplifier);
        float actual_health_percentage = livingEntity.getHealth() / livingEntity.getMaxHealth();
        float heal_amount = livingEntity.getMaxHealth() * (0.05F + (0.05F + amplifier));
        if(actual_health_percentage <= 0.2F){
            livingEntity.heal(heal_amount);
            if(!livingEntity.getWorld().isClient()){
                ParticleHelper.sendBatches(livingEntity, List.of(particles));
                ParticleHelper.sendBatches(livingEntity, List.of(particles1));
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
                ParticleHelper.sendBatches(livingEntity, List.of(particles));
                ParticleHelper.sendBatches(livingEntity, List.of(particles1));
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
