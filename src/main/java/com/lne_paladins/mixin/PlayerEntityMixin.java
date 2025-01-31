package com.lne_paladins.mixin;

import com.lne_paladins.effect.Effects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.spell_engine.api.spell.ParticleBatch;
import net.spell_engine.particle.ParticleHelper;
import net.spell_power.api.SpellSchools;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    private static final ParticleBatch particles = new ParticleBatch(
            "spell_engine:holy_hit",
            ParticleBatch.Shape.SPHERE,
            ParticleBatch.Origin.CENTER,
            null,
            20,
            0.2F,
            0.5F,
            0);
    private static final ParticleBatch particles1 = new ParticleBatch(
            "spell_engine:electric_arc_a",
            ParticleBatch.Shape.PILLAR,
            ParticleBatch.Origin.CENTER,
            null,
            6,
            0.01F,
            0.05F,
            3);

    @Inject(at = @At("TAIL"), method = "attack")
    public void lnePaladins_holyWeapon$attack(Entity target, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        if (player instanceof ServerPlayerEntity && target instanceof LivingEntity livingTarget) {
            if (player.hasStatusEffect(Effects.HOLY_WEAPON)) {
                float healing_power = (float) Objects.requireNonNull(player.getAttributeInstance(SpellSchools.HEALING.attribute)).getValue();
                int effect_amp = player.getStatusEffect(Effects.HOLY_WEAPON).getAmplifier();
                float damage_multiplier = 0.5F;
                if(livingTarget.isUndead()) {
                    damage_multiplier = 1.0F;
                }
                livingTarget.damage(livingTarget.getDamageSources().magic(), (healing_power+effect_amp)*damage_multiplier);
                ParticleHelper.sendBatches(livingTarget, new ParticleBatch[]{particles});
                ParticleHelper.sendBatches(livingTarget, new ParticleBatch[]{particles1});
            }
        }
    }
}
