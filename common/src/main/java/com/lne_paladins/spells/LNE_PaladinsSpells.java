package com.lne_paladins.spells;

import net.minecraft.util.Identifier;
import com.lne_paladins.effect.LNE_PaladinsEffects;
import net.paladins.content.PaladinSounds;
import net.spell_engine.api.datagen.SpellBuilder;
import net.spell_engine.api.spell.ExternalSpellSchools;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.fx.Fx;
import net.spell_engine.api.spell.fx.ParticleGroup;
import net.spell_engine.api.spell.fx.ParticleGroupBuilder;
import net.spell_engine.api.spell.fx.ParticleGroupBuilder.Batches;
import net.spell_engine.api.spell.fx.PlayerAnimation;
import net.spell_engine.api.spell.fx.Sound;
import net.spell_engine.api.spell.tooltip.TooltipTokens;
import net.spell_engine.client.util.Color;
import net.spell_engine.fx.SpellEngineParticles;
import net.spell_power.api.SpellSchools;
import java.util.ArrayList;
import java.util.List;

import static com.lne_paladins.LNE_Paladins_Mod.MOD_ID;

public class LNE_PaladinsSpells {
    public record Entry(Identifier id, Spell spell, String title, String description) {
    }

    public static final List<Entry> entries = new ArrayList<>();

    private static Entry add(Entry entry) {
        entries.add(entry);
        return entry;
    }
    public static Entry paladin_sky_splitter = add(paladin_sky_splitter());
    private static Entry paladin_sky_splitter() {
        var id = Identifier.of(MOD_ID, "paladin_sky_splitter");
        var title = "Templar's Sky Splitter";
        var effect = LNE_PaladinsEffects.TEMPLARS_RETRIBUTION;
        // Was a `SpellTooltip.DescriptionMutator` reading `effect.config().firstModifier()` through
        // `SpellTooltip.bonus(Math.abs(value), operation)`. That is exactly what the declarative effect
        // token expresses, and it reads the same config, so server overrides still apply.
        // Templar's Retribution carries a single modifier (attack damage, +20% ADD_MULTIPLIED_BASE), so
        // the token's blank-attribute fallback is unambiguous and matches `firstModifier()`.
        // The impact applies the effect at amplifier 0, hence amplifier 0 here.
        // `ABS` reproduces the mutator's `Math.abs`, and matches the "Increasing ... by" prose.
        var description = "Call down a ring of falling templar swords around you, dealing {damage} damage."
                + " Increasing the attack damage of the caster by "
                + TooltipTokens.effect(effect.id, 0, null, TooltipTokens.Format.ABS) + ".";

        var spell = SpellBuilder.createSpellActive();
        spell.school = ExternalSpellSchools.PHYSICAL_MELEE;
        spell.range = 0.0F;
        spell.tier = 5;

        spell.active.cast.duration = 0.75F;
        spell.active.cast.animation = PlayerAnimation.of("more_rpg_classes:kneeing_uprising_charge");
        // Continuous emitter - casting particles stay a plain list.
        spell.active.cast.particles = List.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_spark, ParticleGroup.Motion.FLOAT, Color.HOLY)
                        .batch(b -> b.shape(ParticleGroup.Shape.PIPE)
                                .count(4F).speed(0.01F, 0.1F)
                                .verticalOrigin(Batches.FEET)
                                .extent(1.5F)));

        spell.release.animation = PlayerAnimation.of("spell_engine:one_handed_area_release");
        spell.release.sound = new Sound(Identifier.of("more_rpg_classes:holy_release"));
        // `electric_arc_A/B` were retired in 1.10; `ParticleGroupBuilder.electricArc` rebuilds that look
        // on the `lightning_arc_*` textures. Neither site authored scale/colour/max_age, so the helper's
        // baked values override nothing.
        spell.release.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.electricArc(SpellEngineParticles.lightning_arc_A)
                        .batch(b -> b.shape(ParticleGroup.Shape.PILLAR)
                                .count(6F).speed(0.01F, 0.05F)
                                .verticalOrigin(Batches.FEET)
                                .extent(1.0F)),
                ParticleGroupBuilder.electricArc(SpellEngineParticles.lightning_arc_B)
                        .batch(b -> b.shape(ParticleGroup.Shape.PILLAR)
                                .count(8F).speed(0.01F, 0.05F)
                                .verticalOrigin(Batches.FEET)
                                .extent(1.0F)));

        spell.deliver = new Spell.Delivery();
        spell.deliver.type = Spell.Delivery.Type.CUSTOM;
        spell.deliver.custom = new Spell.Delivery.Custom();
        spell.deliver.custom.handler = Identifier.of(MOD_ID, "sky_splitter").toString();

        var damage = SpellBuilder.Impacts.damage(1.2F, 1.5F);
        damage.target_modifiers = List.of(SpellBuilder.ImpactModifiers.extraDamageAgainstUndead());
        damage.school = ExternalSpellSchools.PHYSICAL_MELEE;
        damage.power_blend = List.of(SpellBuilder.Impacts.powerBlend(
                SpellSchools.HEALING, 1F / 3F, true, true, true));
        damage.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_spell, ParticleGroup.Motion.BURST, Color.HOLY)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(15F).speed(0.2F, 0.7F)));
        damage.sound = new Sound(Identifier.of("paladins:holy_shock_damage"));

        var templarsRetribution = SpellBuilder.Impacts.effectSet(
                LNE_PaladinsEffects.TEMPLARS_RETRIBUTION.id.toString(), 8, 0);
        templarsRetribution.action.apply_to_caster = true;
        templarsRetribution.action.status_effect.show_particles = false;

        spell.impacts = List.of(damage, templarsRetribution);

        spell.area_impact = new Spell.AreaImpact();
        spell.area_impact.radius = 3;
        spell.area_impact.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;
        spell.area_impact.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.electricArc(SpellEngineParticles.lightning_arc_A)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(20F).speed(0.8F, 0.9F)),
                ParticleGroupBuilder.electricArc(SpellEngineParticles.lightning_arc_B)
                        .batch(b -> b.shape(ParticleGroup.Shape.SPHERE)
                                .count(20F).speed(0.2F, 0.4F)));
        spell.area_impact.sound = Sound.withVolume(PaladinSounds.judgement_impact.id(), 1.5F);

        SpellBuilder.Cost.cooldown(spell,30);
        SpellBuilder.Cost.item(spell,"runes:healing_stone");

        return new Entry(id, spell, title, description);
    }

    public static Entry holy_prevention = add(holy_prevention());
    private static Entry holy_prevention() {
        var id = Identifier.of(MOD_ID, "holy_prevention");
        var title = "Holy Prevention";
        var description = "Apply a protective effect to target for {effect_duration} seconds, reducing incoming damage.";

        var spell = SpellBuilder.createSpellActive();
        spell.school = SpellSchools.HEALING;
        spell.range = 20.0F;
        spell.tier = 5;

        spell.active.cast = new Spell.Active.Cast();
        spell.active.cast.duration = 1.0F;
        spell.active.cast.animation = PlayerAnimation.of("spell_engine:one_handed_healing_charge");
        spell.active.cast.sound = new Sound(Identifier.of("spell_engine:generic_healing_casting"), 0);
        // Continuous emitter - casting particles stay a plain list.
        spell.active.cast.particles = List.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_spark, ParticleGroup.Motion.FLOAT, Color.HOLY)
                        .batch(b -> b.shape(ParticleGroup.Shape.PIPE)
                                .count(15F).speed(0.05F, 0.1F)
                                .verticalOrigin(Batches.FEET)));

        spell.release.animation = PlayerAnimation.of("spell_engine:one_handed_healing_release");
        spell.release.sound = new Sound(Identifier.of("spell_engine:generic_healing_release"));

        spell.target.type = Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();
        spell.target.aim.use_caster_as_fallback = true;

        var preventionEffect = SpellBuilder.Impacts.effectSet("lne_paladins:holy_prevention",10,0);
        preventionEffect.action.status_effect.amplifier_power_multiplier = 0.25F;
        preventionEffect.action.status_effect.show_particles = false;
        preventionEffect.visuals = Fx.Visuals.of(
                ParticleGroupBuilder.magic(SpellEngineParticles.magic_spark, ParticleGroup.Motion.ASCEND, Color.HOLY)
                        .batch(b -> b.shape(ParticleGroup.Shape.PILLAR)
                                .count(25F).speed(0.02F, 0.15F)
                                .verticalOrigin(Batches.FEET)));
        preventionEffect.sound = new Sound(Identifier.of("spell_engine:generic_healing_impact_1"));

        spell.impacts = List.of(preventionEffect);

        SpellBuilder.Cost.cooldown(spell,40);
        SpellBuilder.Cost.item(spell,"runes:healing_stone");

        return new Entry(id, spell, title, description);
    }
}
