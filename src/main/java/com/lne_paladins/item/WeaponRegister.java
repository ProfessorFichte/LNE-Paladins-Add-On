package com.lne_paladins.item;

import com.lne_paladins.item.weapons.*;
import more_rpg_loot.item.Group;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.more_rpg_classes.custom.MoreSpellSchools;
import net.spell_engine.api.item.ItemConfig;
import net.spell_engine.api.item.weapon.Weapon;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Supplier;

import static com.lne_paladins.LNE_Paladins_Mod.MOD_ID;
import static com.lne_paladins.LNE_Paladins_Mod.tweaksConfig;

public class WeaponRegister {
    public static final ArrayList<Weapon.Entry> entries = new ArrayList<>();

    private static Weapon.Entry entry(String name, Weapon.CustomMaterial material, Item item, ItemConfig.Weapon defaults) {
        return entry(null, name, material, item, defaults);
    }

    private static Weapon.Entry entry(String requiredMod, String name, Weapon.CustomMaterial material, Item item, ItemConfig.Weapon defaults) {
        var entry = new Weapon.Entry(MOD_ID, name, material, item, defaults, null);
        if (entry.isRequiredModInstalled()) {
            entries.add(entry);
        }
        return entry;
    }

    private static Supplier<Ingredient> ingredient(String idString, boolean requirement, Item fallback) {
        var id = new Identifier(idString);
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
    ///ATTACKSPEED_VALUES
    private static final float paladins_claymoreAttackSpeed = -3F;
    private static final float paladins_greatHammerAttackSpeed = -3.2F;
    private static final float paladins_maceAttackSpeed = -2.8F;
    private static final float paladins_staffAttackSpeed = -3F;
    private static final float weaponSpellPower = 3.0F;

    //HOLY STAFF
    private static final float staffAttackDamage = 4;
    private static final float staffSpellPower = 6.0F;
    private static Weapon.Entry elderGuardianStaff(String name, Weapon.CustomMaterial material) {
        return elderGuardianStaff(null, name, material);
    }
    private static Weapon.Entry elderGuardianStaff(String requiredMod, String name, Weapon.CustomMaterial material) {
        var settings = new Item.Settings();
        settings = settings.rarity(Rarity.EPIC).fireproof();
        var item = new SirensStaff(material, settings);
        return entry(requiredMod, name, material, item, new ItemConfig.Weapon(staffAttackDamage, paladins_staffAttackSpeed));
    }

    //CLAYMORES
    private static final float claymoreAttackDamage = 11.5F;
    private static Weapon.Entry claymoreDragon(String name, Weapon.CustomMaterial material) {
        return claymoreDragon(null, name, material);
    }
    private static Weapon.Entry claymoreDragon(String requiredMod, String name, Weapon.CustomMaterial material) {
        var settings = new Item.Settings();
        settings = settings.rarity(Rarity.EPIC).fireproof();
        var item = new DragonClaymore(material, settings);
        return entry(requiredMod, name, material, item, new ItemConfig.Weapon(claymoreAttackDamage, paladins_claymoreAttackSpeed));
    }
    private static Weapon.Entry claymoreElderGuardian(String name, Weapon.CustomMaterial material) {
        return claymoreElderGuardian(null, name, material);
    }
    private static Weapon.Entry claymoreElderGuardian(String requiredMod, String name, Weapon.CustomMaterial material) {
        var settings = new Item.Settings();
        settings = settings.rarity(Rarity.EPIC).fireproof();
        var item = new ElderGuardianClaymore(material, settings);
        return entry(requiredMod, name, material, item, new ItemConfig.Weapon(claymoreAttackDamage, paladins_claymoreAttackSpeed));
    }
    private static Weapon.Entry claymoreWither(String name, Weapon.CustomMaterial material) {
        return claymoreWither(null, name, material);
    }
    private static Weapon.Entry claymoreWither(String requiredMod, String name, Weapon.CustomMaterial material) {
        var settings = new Item.Settings();
        settings = settings.rarity(Rarity.EPIC).fireproof();
        var item = new WitherClaymore(material, settings);
        return entry(requiredMod, name, material, item, new ItemConfig.Weapon(claymoreAttackDamage, paladins_claymoreAttackSpeed));
    }
    private static Weapon.Entry claymoreGlacial(String name, Weapon.CustomMaterial material) {
        return claymoreGlacial(null, name, material);
    }
    private static Weapon.Entry claymoreGlacial(String requiredMod, String name, Weapon.CustomMaterial material) {
        var settings = new Item.Settings();
        settings = settings.rarity(Rarity.EPIC).fireproof();
        var item = new GlacialClaymore(material, settings);
        return entry(requiredMod, name, material, item, new ItemConfig.Weapon(claymoreAttackDamage, paladins_claymoreAttackSpeed));
    }




    public static void register(Map<String, ItemConfig.Weapon> configs) {
        if (!tweaksConfig.value.disable_special_lne_weapons) {
            var dragonRepair = ingredient("loot_n_explore:ender_dragon_scales",
                    FabricLoader.getInstance().isModLoaded("loot_n_explore"), Items.NETHERITE_INGOT);
            var elderGuardianRepair = ingredient("loot_n_explore:elder_guardian_eye",
                    FabricLoader.getInstance().isModLoaded("loot_n_explore"), Items.NETHERITE_INGOT);
            var frostMonarchRepair = ingredient("loot_n_explore:frozen_soul",
                    FabricLoader.getInstance().isModLoaded("loot_n_explore"), Items.NETHERITE_INGOT);
            var witherRepair = ingredient("minecraft:nether_star",
                    FabricLoader.getInstance().isModLoaded("loot_n_explore"), Items.NETHERITE_INGOT);
            //HOLY STAFF
            elderGuardianStaff("sirens_holy_staff",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, elderGuardianRepair))
                    .attribute(ItemConfig.Attribute.bonus(SpellSchools.HEALING.id, staffSpellPower));
            //CLAYMORES
            claymoreDragon("ender_dragon_claymore",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, dragonRepair))
                    .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, weaponSpellPower));
            claymoreElderGuardian("elder_guardian_claymore",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, elderGuardianRepair))
                    .attribute(ItemConfig.Attribute.bonus(MoreSpellSchools.WATER.id, weaponSpellPower));
            claymoreWither("wither_claymore",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, witherRepair))
                    .attribute(ItemConfig.Attribute.bonus(SpellSchools.SOUL.id, weaponSpellPower));
            claymoreGlacial("glacial_claymore",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, frostMonarchRepair))
                    .attribute(ItemConfig.Attribute.bonus(SpellSchools.FROST.id, weaponSpellPower));
            //MACE
            //HAMMER
        }

        Weapon.register(configs, entries, Group.RPG_LOOT_KEY);
    }
}
