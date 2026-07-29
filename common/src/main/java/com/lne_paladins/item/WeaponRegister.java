package com.lne_paladins.item;

import more_rpg_loot.compat.spell_engine.LNE_Abilities;
import more_rpg_loot.item.Group;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.more_rpg_classes.custom.MoreSpellSchools;
import net.more_rpg_classes.custom.MrpgLibSpells;
import net.paladins.content.PaladinSpells;
import net.spell_engine.api.config.AttributeModifier;
import net.spell_engine.api.config.WeaponConfig;
import net.spell_engine.api.spell.container.SpellContainers;
import net.spell_engine.rpg_series.datagen.WeaponSkills;
import net.spell_engine.rpg_series.item.Equipment;
import net.spell_engine.rpg_series.item.Weapon;
import net.spell_power.api.SpellSchools;
import net.spell_engine.api.item.weapon.SpellSwordItem;
import net.spell_engine.api.item.weapon.SpellWeaponItem;
import net.spell_engine.api.item.weapon.StaffItem;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Supplier;

import static com.lne_paladins.LNE_Paladins_Mod.MOD_ID;
import static com.lne_paladins.LNE_Paladins_Mod.tweaksConfig;
import static more_rpg_loot.compat.spell_engine.LNE_Weapons.*;

public class WeaponRegister {
    public static final ArrayList<Weapon.Entry> entries = new ArrayList<>();
    private static Weapon.Entry entry(String name, Weapon.CustomMaterial material, Weapon.Factory factory, WeaponConfig defaults, Equipment.WeaponType weaponType) {
        var entry = new Weapon.Entry(MOD_ID, name, material, factory, defaults, weaponType);
        entries.add(entry);
        return entry;
    }

    private static Supplier<Ingredient> ingredient(String idString, boolean requirement, Item fallback) {
        var id = Identifier.of(idString);
        if (requirement) {
            return () -> {
                return Ingredient.ofItems(fallback);
            };
        } else {
            return () -> {
                var item = Registries.ITEM.get(id);
                var ingredient = item != null ? item : fallback;
                return Ingredient.ofItems(ingredient);
            };
        }
    }
    ///ATTRIBUTE VALUES
    private static final float paladins_claymoreAttackSpeed = -3F;
    private static final float claymoreAttackDamage = 13F;
    private static final float paladins_greatHammerAttackSpeed = -3.2F;
    private static final float hammerAttackDamage = 16.0F;
    private static final float paladins_maceAttackSpeed = -2.8F;
    private static final float maceAttackDamage = 11F;
    private static final float paladins_staffAttackSpeed = -3F;
    private static final float staffAttackDamage = 4;
    private static final float staffSpellPower = 8.0F;
    private static final float weaponSpellPower = 4.0F;
    ///SPELL IDS
    public static Identifier sirens_tears = MrpgLibSpells.sirens_tears.id();

    private static Weapon.Entry healing_staff(String name, Weapon.CustomMaterial material) {
        var entry = entry(name, material, StaffItem::new, new WeaponConfig(staffAttackDamage, paladins_staffAttackSpeed), Equipment.WeaponType.HEALING_STAFF)
                .spellContainer(SpellContainers.forMagicWeapon().withSpellId(PaladinSpells.HOLY_SHOCK.id()));
        entry.weaponAttributesPreset = "staff";
        return entry;
    }
    private static Weapon.Entry claymore(String name, Weapon.CustomMaterial material, float damage) {
        var entry = entry(name, material, SpellSwordItem::new, new WeaponConfig(damage, paladins_claymoreAttackSpeed), Equipment.WeaponType.CLAYMORE)
                .spellContainer(SpellContainers.forMeleeWeapon().withSpellId(WeaponSkills.FLURRY.id()));
        entry.weaponAttributesPreset = "claymore";
        return entry;
    }
    private static Weapon.Entry hammer(String name, Weapon.CustomMaterial material, float damage) {
        var entry = entry(name, material, SpellWeaponItem::new, new WeaponConfig(damage, paladins_greatHammerAttackSpeed), Equipment.WeaponType.HAMMER)
                .spellContainer(SpellContainers.forMeleeWeapon().withSpellId(WeaponSkills.GROUND_SLAM.id()));
        entry.weaponAttributesPreset = "hammer";
        return entry;
    }
    private static Weapon.Entry mace(String name, Weapon.CustomMaterial material, float damage) {
        var entry = entry(name, material, SpellWeaponItem::new, new WeaponConfig(damage, paladins_maceAttackSpeed), Equipment.WeaponType.MACE)
                .spellContainer(SpellContainers.forMeleeWeapon().withSpellId(WeaponSkills.SMASH.id()));
        entry.weaponAttributesPreset = "mace";
        return entry;
    }

    public static void register(Map<String, WeaponConfig> configs) {
        if (!tweaksConfig.value.disable_special_lne_weapons) {
            ///ENDER DRAGON WEAPON THEMES
            claymore("ender_dragon_claymore",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.AMETHYST_SHARD)), claymoreAttackDamage)
                    .withAdditionalSpell(MrpgLibSpells.dragonclaw_melee.id().toString())
                    .translatedName("Ender Blade")
                    .attribute(AttributeModifier.bonus(SpellSchools.ARCANE.id, weaponSpellPower));
            mace("ender_dragon_mace",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.AMETHYST_SHARD)), maceAttackDamage)
                    .withAdditionalSpell(MrpgLibSpells.dragonclaw_melee.id().toString())
                    .translatedName("Dragon´s Maw")
                    .attribute(AttributeModifier.bonus(SpellSchools.ARCANE.id, weaponSpellPower));
            hammer("ender_dragon_great_hammer",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.AMETHYST_SHARD)), hammerAttackDamage)
                    .withAdditionalSpell(MrpgLibSpells.dragonclaw_melee.id().toString())
                    .translatedName("Dragons Nails")
                    .attribute(AttributeModifier.bonus(SpellSchools.ARCANE.id, weaponSpellPower));
            ///OCEAN WEAPON THEMES
            claymore("elder_guardian_claymore",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.PRISMARINE_SHARD)), claymoreAttackDamage)
                    .withAdditionalSpell(MrpgLibSpells.waterbomb_melee.id().toString())
                    .translatedName("Sea King's Blade")
                    .attribute(AttributeModifier.bonus(MoreSpellSchools.WATER.id, weaponSpellPower));
            mace("elder_guardian_mace",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.PRISMARINE_SHARD)), maceAttackDamage)
                    .withAdditionalSpell(MrpgLibSpells.waterbomb_melee.id().toString())
                    .translatedName("Sea Star")
                    .attribute(AttributeModifier.bonus(MoreSpellSchools.WATER.id, weaponSpellPower));
            hammer("elder_guardian_great_hammer",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.PRISMARINE_SHARD)), hammerAttackDamage)
                    .withAdditionalSpell(MrpgLibSpells.waterbomb_melee.id().toString())
                    .translatedName("Tsunamis Wrath")
                    .attribute(AttributeModifier.bonus(MoreSpellSchools.WATER.id, weaponSpellPower));
            healing_staff("elder_guardian_holy_staff",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.PRISMARINE_SHARD)))
                    .spellContainer(SpellContainers.forMagicWeapon().withSpellId(sirens_tears))
                    .translatedName("Siren's Holy Staff")
                    .attribute(AttributeModifier.bonus(SpellSchools.HEALING.id, staffSpellPower))
                    .attribute(AttributeModifier.bonus(MoreSpellSchools.WATER.id, staffSpellPower));
            ///WITHER WEAPON THEMES
            claymore("wither_claymore",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.BONE)), claymoreAttackDamage)
                    .withAdditionalSpell(MrpgLibSpells.wither_pulse_melee.id().toString())
                    .translatedName("Withered Claymore")
                    .attribute(AttributeModifier.bonus(SpellSchools.SOUL.id, weaponSpellPower));
            mace("wither_mace",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.BONE)), maceAttackDamage)
                    .withAdditionalSpell(MrpgLibSpells.wither_pulse_melee.id().toString())
                    .translatedName("Withered Mace")
                    .attribute(AttributeModifier.bonus(SpellSchools.SOUL.id, weaponSpellPower));
            hammer("wither_great_hammer",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.BONE)), hammerAttackDamage)
                    .withAdditionalSpell(MrpgLibSpells.wither_pulse_melee.id().toString())
                    .translatedName("Withered Crusher")
                    .attribute(AttributeModifier.bonus(SpellSchools.SOUL.id, weaponSpellPower));
            ///GLACIAL WEAPON THEMES
            claymore("glacial_claymore",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.ICE)), claymoreAttackDamage)
                    .withAdditionalSpell(MrpgLibSpells.avalanche_melee.id().toString())
                    .translatedName("Glaciers Edge")
                    .attribute(AttributeModifier.bonus(SpellSchools.FROST.id, weaponSpellPower));
            mace("glacial_mace",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.ICE)), maceAttackDamage)
                    .withAdditionalSpell(MrpgLibSpells.avalanche_melee.id().toString())
                    .translatedName("Icicle Crusher")
                    .attribute(AttributeModifier.bonus(SpellSchools.FROST.id, weaponSpellPower));
            hammer("glacial_great_hammer",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.ICE)), hammerAttackDamage)
                    .withAdditionalSpell(MrpgLibSpells.avalanche_melee.id().toString())
                    .translatedName("Frozen Mallet")
                    .attribute(AttributeModifier.bonus(SpellSchools.FROST.id, weaponSpellPower));

        }

        entries.forEach(entry -> entry.rarity = Rarity.RARE);
        Weapon.register(configs, entries, Group.RPG_LOOT_KEY);
    }
}
