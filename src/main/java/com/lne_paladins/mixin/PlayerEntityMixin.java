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
            ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,
            20, 0.2F, 0.5F, 0);
    private static final ParticleBatch particles1 = new ParticleBatch(
            "spell_engine:electric_arc_a",
            ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.CENTER, null,
            6, 0.01F, 0.05F, 0, 1.5F);
    private static final ParticleBatch particles2 = new ParticleBatch(
            "spell_engine:healing_ascend",
            ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET, null,
            5, 0.02F, 0.15F, 0, 0.5F);

    @Inject(at = @At("TAIL"), method = "attack")
    public void lnePaladins_holyWeapon$attack(Entity target, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        if (player instanceof ServerPlayerEntity && target instanceof LivingEntity livingTarget && player.hasStatusEffect(Effects.HOLY_WEAPON)) {
            int effect_amp = Objects.requireNonNull(player.getStatusEffect(Effects.HOLY_WEAPON)).getAmplifier() + 1;
            if(livingTarget.isUndead()) {
                float healing_power = (float) Objects.requireNonNull(player.getAttributeInstance(SpellSchools.HEALING.attribute)).getValue();
                float damage_multiplier = 1.0F;
                float damage_calc = (healing_power*damage_multiplier);
                livingTarget.damage(livingTarget.getDamageSources().magic(), damage_calc);
            }
            player.heal(effect_amp);

            ParticleHelper.sendBatches(livingTarget, new ParticleBatch[]{particles});
            ParticleHelper.sendBatches(livingTarget, new ParticleBatch[]{particles1});
            ParticleHelper.sendBatches(player, new ParticleBatch[]{particles2});

        }
    }

}
