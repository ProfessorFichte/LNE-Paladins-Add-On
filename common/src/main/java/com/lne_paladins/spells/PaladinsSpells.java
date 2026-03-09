package com.lne_paladins.spells;

import net.minecraft.util.Identifier;
import net.more_rpg_classes.custom.MoreSpellSchools;
import net.spell_engine.api.datagen.SpellBuilder;
import net.spell_engine.api.spell.ExternalSpellSchools;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.fx.ParticleBatch;
import net.spell_engine.api.spell.fx.PlayerAnimation;
import net.spell_engine.api.spell.fx.Sound;
import net.spell_engine.api.util.TriState;
import net.spell_engine.client.util.Color;
import net.spell_engine.fx.SpellEngineParticles;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.lne_paladins.LNE_Paladins_Mod.MOD_ID;

public class PaladinsSpells {
    public record Entry(Identifier id, Spell spell, String title, String description,
                        @Nullable net.spell_engine.client.gui.SpellTooltip.DescriptionMutator mutator) {
    }

    public static final List<Entry> entries = new ArrayList<>();

    private static Entry add(Entry entry) {
        entries.add(entry);
        return entry;
    }

    // ===== HELPER METHODS =====
    private static Spell.Impact.TargetModifier createDenyModifier(String entityTypeTag) {
        var modifier = new Spell.Impact.TargetModifier();
        var condition = new Spell.TargetCondition();
        condition.entity_type = entityTypeTag;
        modifier.conditions = List.of(condition);
        modifier.execute = TriState.DENY;
        return modifier;
    }

    // ===== PASSIVE SPELL DEFINITIONS =====
    public static Entry elder_guardian_shield = add(elder_guardian_shield());
    private static Entry elder_guardian_shield() {
        var id = Identifier.of(MOD_ID, "elder_guardian_shield");
        var title = "Elder Guardian Shield";
        var description = "On shield block: {trigger_chance} chance to inflict bleeding for {effect_duration} seconds and deal {damage} damage.";

        var spell = new Spell();
        spell.school = ExternalSpellSchools.PHYSICAL_MELEE;
        spell.range = 0.0F;
        spell.tier = 8;
        spell.type = Spell.Type.PASSIVE;
        spell.passive = new Spell.Passive();

        var trigger = new Spell.Trigger();
        trigger.type = Spell.Trigger.Type.SHIELD_BLOCK;
        trigger.chance = 0.5F;
        spell.passive.triggers = List.of(trigger);


        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var bleedingEffect = SpellBuilder.Impacts.effectSet("more_rpg_classes:bleeding",7,0);
        bleedingEffect.target_modifiers = List.of(
            createDenyModifier("#minecraft:undead")
        );
        bleedingEffect.action.status_effect.amplifier_power_multiplier = 0.3F;
        bleedingEffect.action.status_effect.show_particles = false;
        bleedingEffect.particles = new ParticleBatch[]{
            new ParticleBatch(
                "spell_engine:dripping_blood",
                ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                10.0F, 0.05F, 0.3F
            )
        };

        var damage = SpellBuilder.Impacts.damage(0.2F,0.25F);
        damage.particles = new ParticleBatch[]{
            new ParticleBatch(
                    SpellEngineParticles.MagicParticles.get(
                            SpellEngineParticles.MagicParticles.Shape.SPARK,
                            SpellEngineParticles.MagicParticles.Motion.FLOAT).id().toString(),
                ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                10.0F, 0.3F, 0.35F
            ).color(3217014783L)
        };

        spell.impacts = List.of(bleedingEffect, damage);

        SpellBuilder.Cost.cooldown(spell,10F);
        spell.cost.batching = true;
        spell.cost.cooldown.hosting_item = false;

        return new Entry(id, spell, title, description, null);
    }

    public static Entry glacial_shield = add(glacial_shield());
    private static Entry glacial_shield() {
        var id = Identifier.of(MOD_ID, "glacial_shield");
        var title = "Glacial Shield";
        var description = "On shield block: {trigger_chance} chance to freeze nearby enemies for {effect_duration} seconds.";

        var spell = new Spell();
        spell.school = ExternalSpellSchools.PHYSICAL_MELEE;
        spell.range = 2.5F;
        spell.tier = 8;
        spell.type = Spell.Type.PASSIVE;
        spell.passive = new Spell.Passive();

        var trigger = new Spell.Trigger();
        trigger.type = Spell.Trigger.Type.SHIELD_BLOCK;
        trigger.chance = 0.2F;
        spell.passive.triggers = List.of(trigger);

        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.vertical_range_multiplier = 1.0F;

        spell.release.sound = new Sound(Identifier.of("spell_engine:generic_frost_release"));
        spell.release.particles = new ParticleBatch[]{
            new ParticleBatch(
            SpellEngineParticles.snowflake.id().toString(),
                ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER,
                60.0F, 0.1F, 0.3F
            ),
            new ParticleBatch(
                SpellEngineParticles.frost_shard.id().toString(),
                ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER,
                60.0F, 0.3F, 0.6F
            )
        };
        var rangedParticle = new ParticleBatch(
            SpellEngineParticles.area_effect_293.id().toString(),
            ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.GROUND,
            1.0F, 0.0F, 0.0F
        ).color(2582052863L);
        rangedParticle.scale = 0.4F;
        spell.release.particles_scaled_with_ranged = new ParticleBatch[]{
            rangedParticle
        };

        var frozenEffect = SpellBuilder.Impacts.effectSet("more_rpg_classes:frozen_solid",3,0);
        frozenEffect.action.status_effect.show_particles = false;
        frozenEffect.particles = new ParticleBatch[]{
            new ParticleBatch(
                SpellEngineParticles.snowflake.id().toString(),
                ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                25.0F, 0.1F, 0.4F
            ),
            new ParticleBatch(
                    SpellEngineParticles.MagicParticles.get(
                            SpellEngineParticles.MagicParticles.Shape.FROST,
                            SpellEngineParticles.MagicParticles.Motion.BURST).id().toString(),
                ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                30.0F, 0.2F, 0.7F
            )
        };
        frozenEffect.sound = new Sound(Identifier.of("spell_engine:generic_frost_impact"));

        spell.impacts = List.of(frozenEffect);

        SpellBuilder.Cost.cooldown(spell,10F);
        spell.cost.batching = true;
        spell.cost.cooldown.hosting_item = false;

        return new Entry(id, spell, title, description, null);
    }

    public static Entry wither_shield = add(wither_shield());
    private static Entry wither_shield() {
        var id = Identifier.of(MOD_ID, "wither_shield");
        var title = "Wither Shield";
        var description = "On shield block: {trigger_chance} chance to shoot 3 wither skulls dealing {damage} damage and inflicting wither.";

        var spell = new Spell();
        spell.school = ExternalSpellSchools.PHYSICAL_MELEE;
        spell.range = 20.0F;
        spell.tier = 8;
        spell.type = Spell.Type.PASSIVE;
        spell.passive = new Spell.Passive();

        var trigger = new Spell.Trigger();
        trigger.type = Spell.Trigger.Type.SHIELD_BLOCK;
        trigger.chance = 0.3F;
        spell.passive.triggers = List.of(trigger);

        spell.target.type = Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();

        spell.release.particles = new ParticleBatch[]{
            new ParticleBatch(
                "smoke",
                ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                40.0F, 0.6F, 0.8F
            )
        };

        spell.deliver = new Spell.Delivery();
        spell.deliver.type = Spell.Delivery.Type.PROJECTILE;

        spell.deliver.projectile = new Spell.Delivery.ShootProjectile();
        var centerOffset = new Spell.Delivery.ShootProjectile.DirectionOffset();
        var rightOffset = new Spell.Delivery.ShootProjectile.DirectionOffset();
        rightOffset.yaw = 30.0F;
        var leftOffset = new Spell.Delivery.ShootProjectile.DirectionOffset();
        leftOffset.yaw = -30.0F;
        spell.deliver.projectile.direction_offsets = new Spell.Delivery.ShootProjectile.DirectionOffset[]{
            centerOffset,
            rightOffset,
            leftOffset
        };
        spell.deliver.projectile.direct_towards_target = true;
        spell.deliver.projectile.launch_properties.velocity = 1.0F;
        spell.deliver.projectile.launch_properties.extra_launch_count = 2;
        spell.deliver.projectile.launch_properties.extra_launch_delay = 10;

        spell.deliver.projectile.projectile = new Spell.ProjectileData();
        spell.deliver.projectile.projectile.divergence = 5.0F;
        spell.deliver.projectile.projectile.client_data = new Spell.ProjectileData.Client();
        var travelParticle = new ParticleBatch(
            "smoke",
            ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER,
            2.0F, 0.6F, 0.9F
        );
        travelParticle.rotation = ParticleBatch.Rotation.LOOK;
        spell.deliver.projectile.projectile.client_data.travel_particles = new ParticleBatch[]{
            travelParticle
        };
        spell.deliver.projectile.projectile.client_data.model = new Spell.ProjectileModel();
        spell.deliver.projectile.projectile.client_data.model.model_id = "loot_n_explore:spell_projectile/wither_skull";
        spell.deliver.projectile.projectile.client_data.model.scale = 1.5F;
        spell.deliver.projectile.projectile.client_data.model.rotate_degrees_per_tick = 0.0F;

        var witherEffect = SpellBuilder.Impacts.effectSet("wither",5,1);
        witherEffect.action.status_effect.amplifier_power_multiplier = 0.25F;
        witherEffect.action.status_effect.show_particles = false;
        witherEffect.particles = new ParticleBatch[]{
            new ParticleBatch(
                    SpellEngineParticles.MagicParticles.get(
                            SpellEngineParticles.MagicParticles.Shape.SKULL,
                            SpellEngineParticles.MagicParticles.Motion.DECELERATE).id().toString(),
                ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                25.0F, 0.2F, 0.25F
            ).color(858993663L)
        };

        var damage = SpellBuilder.Impacts.damage(0.1F,0.25F);
        damage.particles = new ParticleBatch[]{
            new ParticleBatch(
                "smoke",
                ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                10.0F, 0.3F, 0.35F
            )
        };
        damage.sound = new Sound(Identifier.of("entity.generic.explode"));

        spell.impacts = List.of(witherEffect, damage);

        SpellBuilder.Cost.cooldown(spell,10F);
        spell.cost.batching = true;
        spell.cost.cooldown.hosting_item = false;

        return new Entry(id, spell, title, description, null);
    }

    public static Entry ender_dragon_shield = add(ender_dragon_shield());
    private static Entry ender_dragon_shield() {
        var id = Identifier.of(MOD_ID, "ender_dragon_shield");
        var title = "Ender Dragon Shield";
        var description = "On shield block: {trigger_chance} chance to create an explosive burst dealing {damage} damage to nearby enemies.";

        var spell = new Spell();
        spell.school = ExternalSpellSchools.PHYSICAL_MELEE;
        spell.range = 3.5F;
        spell.tier = 8;
        spell.type = Spell.Type.PASSIVE;
        spell.passive = new Spell.Passive();

        var trigger = new Spell.Trigger();
        trigger.type = Spell.Trigger.Type.SHIELD_BLOCK;
        trigger.chance = 0.4F;
        spell.passive.triggers = List.of(trigger);

        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.vertical_range_multiplier = 1.0F;

        spell.release.sound = new Sound(Identifier.of("entity.generic.explode"));
        spell.release.particles = new ParticleBatch[]{
            new ParticleBatch(
                    SpellEngineParticles.MagicParticles.get(
                            SpellEngineParticles.MagicParticles.Shape.ARCANE,
                            SpellEngineParticles.MagicParticles.Motion.BURST).id().toString(),
                ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER,
                130.0F, 0.2F, 1.5F
            ).color(Color.ARCANE.toRGBA()),
            new ParticleBatch(
                    SpellEngineParticles.MagicParticles.get(
                            SpellEngineParticles.MagicParticles.Shape.STRIPE,
                            SpellEngineParticles.MagicParticles.Motion.BURST).id().toString(),
                ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER,
                130.0F, 0.8F, 1.9F
            ).color(Color.ARCANE.toRGBA())
        };

        var damage = SpellBuilder.Impacts.damage(0.4F,1.0F);
        damage.particles = new ParticleBatch[]{
            new ParticleBatch(
                    SpellEngineParticles.MagicParticles.get(
                            SpellEngineParticles.MagicParticles.Shape.SPELL,
                            SpellEngineParticles.MagicParticles.Motion.BURST).id().toString(),
                ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                30.0F, 0.2F, 1.2F
            ).color(4284940287L)
        };

        spell.impacts = List.of(damage);

        SpellBuilder.Cost.cooldown(spell,10.0F);
        spell.cost.batching = true;
        spell.cost.cooldown.hosting_item = false;

        return new Entry(id, spell, title, description, null);
    }

    public static Entry sirens_tears = add(sirens_tears());
    private static Entry sirens_tears() {
        var id = Identifier.of(MOD_ID, "sirens_tears");
        var title = "Siren's Tears";
        var description = "On healing: {trigger_chance} chance to remove harmful effects and apply regeneration for {effect_duration} seconds.";

        var spell = new Spell();
        spell.school = SpellSchools.HEALING;
        spell.range = 0.0F;
        spell.tier = 7;
        spell.type = Spell.Type.PASSIVE;
        spell.passive = new Spell.Passive();

        var trigger = new Spell.Trigger();
        trigger.type = Spell.Trigger.Type.SPELL_IMPACT_SPECIFIC;
        trigger.chance = 0.3F;
        trigger.impact = new Spell.Trigger.ImpactCondition();
        trigger.impact.impact_type = "HEAL";
        spell.passive.triggers = List.of(trigger);

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var removeEffects = SpellBuilder.Impacts.effectCleanse();
        removeEffects.particles = new ParticleBatch[]{
            new ParticleBatch(
                "more_rpg_classes:water_circle",
                ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.FEET,
                1.0F, 0.2F, 1.0F
            ),
            new ParticleBatch(
                    SpellEngineParticles.MagicParticles.get(
                            SpellEngineParticles.MagicParticles.Shape.SPELL,
                            SpellEngineParticles.MagicParticles.Motion.DECELERATE).id().toString(),
                ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.FEET,
                15.0F, 0.3F, 0.3F
            ).color(4294954239L)
        };

        var regenEffect = SpellBuilder.Impacts.effectSet("regeneration",6,0);
        regenEffect.action.status_effect.amplifier_power_multiplier = 0.15F;
        regenEffect.action.status_effect.show_particles = false;

        spell.impacts = List.of(removeEffects, regenEffect);

        SpellBuilder.Cost.cooldown(spell,20);
        spell.cost.batching = true;

        return new Entry(id, spell, title, description, null);
    }

    // ===== ACTIVE SPELL DEFINITIONS =====
    public static Entry holy_weapon = add(holy_weapon());
    private static Entry holy_weapon() {
        var id = Identifier.of(MOD_ID, "holy_weapon");
        var title = "Holy Weapon";
        var description = "Empower your weapon for {stash_duration} seconds. Melee attacks deal {damage} damage and heal you.";

        var spell = SpellBuilder.createSpellActive();
        spell.school = SpellSchools.HEALING;
        spell.range = 0.0F;
        spell.tier = 5;

        spell.active.cast = new Spell.Active.Cast();
        spell.active.cast.duration = 0.5F;
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
        spell.deliver.type = Spell.Delivery.Type.STASH_EFFECT;
        spell.deliver.stash_effect = new Spell.Delivery.StashEffect();
        spell.deliver.stash_effect.id = "lne_paladins:holy_weapon";
        spell.deliver.stash_effect.amplifier = 0;
        spell.deliver.stash_effect.duration = 10.0F;
        spell.deliver.stash_effect.consume = 0;
        var stashMeleeTrigger = new Spell.Trigger();
        stashMeleeTrigger.type = Spell.Trigger.Type.MELEE_IMPACT;
        spell.deliver.stash_effect.triggers = List.of(stashMeleeTrigger);

        var damage = SpellBuilder.Impacts.damage(0.4F);
        damage.attribute = "minecraft:generic.attack_damage";
        damage.particles = new ParticleBatch[]{
            new ParticleBatch(
                    SpellEngineParticles.MagicParticles.get(
                            SpellEngineParticles.MagicParticles.Shape.SPELL,
                            SpellEngineParticles.MagicParticles.Motion.BURST).id().toString(),
                ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                10.0F, 0.2F, 0.7F
            ).color(Color.HOLY.toRGBA()),
            new ParticleBatch(
                SpellEngineParticles.electric_arc_A.id().toString(),
                ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.CENTER,
                6.0F, 0.01F, 0.05F
            )
        };
        damage.sound = new Sound(Identifier.of("paladins:holy_shock_damage"));

        var heal = SpellBuilder.Impacts.heal(0.15F);
        heal.attribute = "minecraft:generic.attack_damage";
        heal.action.apply_to_caster = true;
        heal.particles = new ParticleBatch[]{
            new ParticleBatch(
                    SpellEngineParticles.MagicParticles.get(
                            SpellEngineParticles.MagicParticles.Shape.SPARK,
                            SpellEngineParticles.MagicParticles.Motion.FLOAT).id().toString(),
                ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.CENTER,
                15.0F, 0.02F, 0.1F
            ).color(Color.HOLY.toRGBA()),
            new ParticleBatch(
                    SpellEngineParticles.MagicParticles.get(
                            SpellEngineParticles.MagicParticles.Shape.HEAL,
                            SpellEngineParticles.MagicParticles.Motion.ASCEND).id().toString(),
                ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.FEET,
                10.0F, 0.02F, 0.15F
            ).color(Color.GREEN.toRGBA())
        };
        heal.sound = new Sound(Identifier.of("spell_engine:generic_healing_impact_1"));

        spell.impacts = List.of(damage, heal);

        SpellBuilder.Cost.cooldown(spell,30);
        SpellBuilder.Cost.item(spell,"runes:healing_stone");

        return new Entry(id, spell, title, description, null);
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
