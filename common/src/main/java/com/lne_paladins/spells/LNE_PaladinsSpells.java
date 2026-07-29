package com.lne_paladins.spells;

import net.minecraft.util.Identifier;
import com.lne_paladins.effect.LNE_PaladinsEffects;
import net.paladins.content.PaladinSounds;
import net.spell_engine.api.datagen.SpellBuilder;
import net.spell_engine.api.effect.SpellEngineEffects;
import net.spell_engine.api.spell.ExternalSpellSchools;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.fx.ParticleBatch;
import net.spell_engine.api.spell.fx.PlayerAnimation;
import net.spell_engine.api.spell.fx.Sound;
import net.spell_engine.api.util.TriState;
import net.spell_engine.client.gui.SpellTooltip;
import net.spell_engine.client.util.Color;
import net.spell_engine.fx.SpellEngineParticles;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.lne_paladins.LNE_Paladins_Mod.MOD_ID;

public class LNE_PaladinsSpells {
    public record Entry(Identifier id, Spell spell, String title, String description,
                        @Nullable net.spell_engine.client.gui.SpellTooltip.DescriptionMutator mutator) {
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
        var description = "Call down a ring of falling templar swords around you, dealing {damage} damage. Increasing the attack damage of the caster by {bonus}.";
        var effect = LNE_PaladinsEffects.TEMPLARS_RETRIBUTION;
        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var modifier = effect.config().firstModifier();
            var bonus = SpellTooltip.bonus(Math.abs(modifier.value), modifier.operation);
            return args.description()
                    .replace("{bonus}", bonus);
        };

        var spell = SpellBuilder.createSpellActive();
        spell.school = ExternalSpellSchools.PHYSICAL_MELEE;
        spell.range = 0.0F;
        spell.tier = 5;

        spell.active.cast.duration = 0.75F;
        spell.active.cast.animation = PlayerAnimation.of("more_rpg_classes:kneeing_uprising_charge");
        var castParticle = new ParticleBatch(
                SpellEngineParticles.MagicParticles.get(
                        SpellEngineParticles.MagicParticles.Shape.SPARK,
                        SpellEngineParticles.MagicParticles.Motion.FLOAT).id().toString(),
            ParticleBatch.Shape.PIPE, ParticleBatch.Origin.FEET,
            4.0F, 0.01F, 0.1F
        ).color(Color.HOLY.toRGBA());
        castParticle.extent = 1.5F;
        spell.active.cast.particles = new ParticleBatch[]{
            castParticle
        };

        spell.release.animation = PlayerAnimation.of("spell_engine:one_handed_area_release");
        spell.release.sound = new Sound(Identifier.of("more_rpg_classes:holy_release"));
        var releaseParticle1 = new ParticleBatch(
            SpellEngineParticles.electric_arc_A.id().toString(),
            ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET,
            6.0F, 0.01F, 0.05F
        );
        releaseParticle1.extent = 1.0F;
        var releaseParticle2 = new ParticleBatch(
            SpellEngineParticles.electric_arc_B.id().toString(),
            ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET,
            8.0F, 0.01F, 0.05F
        );
        releaseParticle2.extent = 1.0F;
        spell.release.particles = new ParticleBatch[]{
            releaseParticle1,
            releaseParticle2
        };

        spell.deliver = new Spell.Delivery();
        spell.deliver.type = Spell.Delivery.Type.CUSTOM;
        spell.deliver.custom = new Spell.Delivery.Custom();
        spell.deliver.custom.handler = Identifier.of(MOD_ID, "sky_splitter").toString();

        var damage = SpellBuilder.Impacts.damage(1.2F, 1.5F);
        damage.target_modifiers = List.of(SpellBuilder.ImpactModifiers.extraDamageAgainstUndead());
        damage.school = ExternalSpellSchools.PHYSICAL_MELEE;
        damage.power_blend = List.of(SpellBuilder.Impacts.powerBlend(
                SpellSchools.HEALING, 1F / 3F, true, true, true));
        damage.particles = new ParticleBatch[]{
            new ParticleBatch(
                    SpellEngineParticles.MagicParticles.get(
                            SpellEngineParticles.MagicParticles.Shape.SPELL,
                            SpellEngineParticles.MagicParticles.Motion.BURST).id().toString(),
                ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                15.0F, 0.2F, 0.7F
            ).color(Color.HOLY.toRGBA())
        };
        damage.sound = new Sound(Identifier.of("paladins:holy_shock_damage"));

        var templarsRetribution = SpellBuilder.Impacts.effectSet(
                LNE_PaladinsEffects.TEMPLARS_RETRIBUTION.id.toString(), 8, 0);
        templarsRetribution.action.apply_to_caster = true;
        templarsRetribution.action.status_effect.show_particles = false;

        spell.impacts = List.of(damage, templarsRetribution);

        spell.area_impact = new Spell.AreaImpact();
        spell.area_impact.radius = 3;
        spell.area_impact.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;
        spell.area_impact.particles = new ParticleBatch[] {
                new ParticleBatch(
                        SpellEngineParticles.electric_arc_A.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        20, 0.8F, 0.9F),
                new ParticleBatch(
                        SpellEngineParticles.electric_arc_B.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        20, 0.2F, 0.4F),
        };
        spell.area_impact.sound = Sound.withVolume(PaladinSounds.judgement_impact.id(), 1.5F);

        SpellBuilder.Cost.cooldown(spell,30);
        SpellBuilder.Cost.item(spell,"runes:healing_stone");

        return new Entry(id, spell, title, description, mutator);
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
        spell.active.cast.particles = new ParticleBatch[]{
            new ParticleBatch(
                    SpellEngineParticles.MagicParticles.get(
                            SpellEngineParticles.MagicParticles.Shape.SPARK,
                            SpellEngineParticles.MagicParticles.Motion.FLOAT).id().toString(),
                ParticleBatch.Shape.PIPE, ParticleBatch.Origin.FEET,
                15.0F, 0.05F, 0.1F
            ).color(Color.HOLY.toRGBA())
        };

        spell.release.animation = PlayerAnimation.of("spell_engine:one_handed_healing_release");
        spell.release.sound = new Sound(Identifier.of("spell_engine:generic_healing_release"));

        spell.target.type = Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();
        spell.target.aim.use_caster_as_fallback = true;

        var preventionEffect = SpellBuilder.Impacts.effectSet("lne_paladins:holy_prevention",10,0);
        preventionEffect.action.status_effect.amplifier_power_multiplier = 0.25F;
        preventionEffect.action.status_effect.show_particles = false;
        preventionEffect.particles = new ParticleBatch[]{
            new ParticleBatch(
                    SpellEngineParticles.MagicParticles.get(
                            SpellEngineParticles.MagicParticles.Shape.SPARK,
                            SpellEngineParticles.MagicParticles.Motion.ASCEND).id().toString(),
                ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET,
                25.0F, 0.02F, 0.15F
            ).color(Color.HOLY.toRGBA())
        };
        preventionEffect.sound = new Sound(Identifier.of("spell_engine:generic_healing_impact_1"));

        spell.impacts = List.of(preventionEffect);

        SpellBuilder.Cost.cooldown(spell,40);
        SpellBuilder.Cost.item(spell,"runes:healing_stone");

        return new Entry(id, spell, title, description, null);
    }
}
