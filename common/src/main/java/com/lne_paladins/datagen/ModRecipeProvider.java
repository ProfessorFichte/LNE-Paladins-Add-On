package com.lne_paladins.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.datagen.SmithingRecipeGenerator;

public class ModRecipeProvider extends SmithingRecipeGenerator {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output, "lne_paladins");
    }

    @Override
    public void generate() {
        // Templates and additions from loot_n_explore mod
        var dragonTemplate = Identifier.of("loot_n_explore", "dragon_upgrade_smithing_template");
        var guardianTemplate = Identifier.of("loot_n_explore", "guardian_upgrade_smithing_template");
        var witherTemplate = Identifier.of("loot_n_explore", "wither_upgrade_smithing_template");
        var frostMonarchTemplate = Identifier.of("loot_n_explore", "frostmonarch_upgrade_smithing_template");

        var dragonScales = Identifier.of("loot_n_explore", "ender_dragon_scales");
        var guardianEye = Identifier.of("loot_n_explore", "elder_guardian_eye");
        var witherSpine = Identifier.of("loot_n_explore", "wither_spine");
        var frozenSoul = Identifier.of("loot_n_explore", "frozen_soul");

        // Base items from paladins mod
        var netheriteClaymore = Identifier.of("paladins", "netherite_claymore");
        var netheriteGreatHammer = Identifier.of("paladins", "netherite_great_hammer");
        var netheriteMace = Identifier.of("paladins", "netherite_mace");
        var netheriteKiteShield = Identifier.of("paladins", "netherite_kite_shield");
        var netheriteHealingStaff = Identifier.of("paladins", "netherite_holy_staff");

        // ENDER DRAGON WEAPONS
        createSmithingTransformRecipe(
            "ender_dragon_claymore_smithing",
            Registries.ITEM.get(netheriteClaymore),
            dragonTemplate,
            dragonScales,
            Registries.ITEM.get(Identifier.of("lne_paladins", "ender_dragon_claymore")),
            "loot_n_explore"
        );

        createSmithingTransformRecipe(
            "ender_dragon_mace_smithing",
            Registries.ITEM.get(netheriteMace),
            dragonTemplate,
            dragonScales,
            Registries.ITEM.get(Identifier.of("lne_paladins", "ender_dragon_mace")),
            "loot_n_explore"
        );

        createSmithingTransformRecipe(
            "ender_dragon_great_hammer_smithing",
            Registries.ITEM.get(netheriteGreatHammer),
            dragonTemplate,
            dragonScales,
            Registries.ITEM.get(Identifier.of("lne_paladins", "ender_dragon_great_hammer")),
            "loot_n_explore"
        );

        createSmithingTransformRecipe(
            "ender_dragon_shield_smithing",
            Registries.ITEM.get(netheriteKiteShield),
            dragonTemplate,
            dragonScales,
            Registries.ITEM.get(Identifier.of("lne_paladins", "ender_dragon_shield")),
            "loot_n_explore"
        );

        // ELDER GUARDIAN WEAPONS
        createSmithingTransformRecipe(
            "elder_guardian_claymore_smithing",
            Registries.ITEM.get(netheriteClaymore),
            guardianTemplate,
            guardianEye,
            Registries.ITEM.get(Identifier.of("lne_paladins", "elder_guardian_claymore")),
            "loot_n_explore"
        );

        createSmithingTransformRecipe(
            "elder_guardian_mace_smithing",
            Registries.ITEM.get(netheriteMace),
            guardianTemplate,
            guardianEye,
            Registries.ITEM.get(Identifier.of("lne_paladins", "elder_guardian_mace")),
            "loot_n_explore"
        );

        createSmithingTransformRecipe(
            "elder_guardian_great_hammer_smithing",
            Registries.ITEM.get(netheriteGreatHammer),
            guardianTemplate,
            guardianEye,
            Registries.ITEM.get(Identifier.of("lne_paladins", "elder_guardian_great_hammer")),
            "loot_n_explore"
        );

        createSmithingTransformRecipe(
            "elder_guardian_holy_staff_smithing",
            Registries.ITEM.get(netheriteHealingStaff),
            guardianTemplate,
            guardianEye,
            Registries.ITEM.get(Identifier.of("lne_paladins", "elder_guardian_holy_staff")),
            "loot_n_explore"
        );

        createSmithingTransformRecipe(
            "elder_guardian_shield_smithing",
            Registries.ITEM.get(netheriteKiteShield),
            guardianTemplate,
            guardianEye,
            Registries.ITEM.get(Identifier.of("lne_paladins", "elder_guardian_shield")),
            "loot_n_explore"
        );

        // WITHER WEAPONS
        createSmithingTransformRecipe(
            "wither_claymore_smithing",
            Registries.ITEM.get(netheriteClaymore),
            witherTemplate,
            witherSpine,
            Registries.ITEM.get(Identifier.of("lne_paladins", "wither_claymore")),
            "loot_n_explore"
        );

        createSmithingTransformRecipe(
            "wither_mace_smithing",
            Registries.ITEM.get(netheriteMace),
            witherTemplate,
            witherSpine,
            Registries.ITEM.get(Identifier.of("lne_paladins", "wither_mace")),
            "loot_n_explore"
        );

        createSmithingTransformRecipe(
            "wither_great_hammer_smithing",
            Registries.ITEM.get(netheriteGreatHammer),
            witherTemplate,
            witherSpine,
            Registries.ITEM.get(Identifier.of("lne_paladins", "wither_great_hammer")),
            "loot_n_explore"
        );

        createSmithingTransformRecipe(
            "wither_shield_smithing",
            Registries.ITEM.get(netheriteKiteShield),
            witherTemplate,
            witherSpine,
            Registries.ITEM.get(Identifier.of("lne_paladins", "wither_shield")),
            "loot_n_explore"
        );

        // GLACIAL WEAPONS
        createSmithingTransformRecipe(
            "glacial_claymore_smithing",
            Registries.ITEM.get(netheriteClaymore),
            frostMonarchTemplate,
            frozenSoul,
            Registries.ITEM.get(Identifier.of("lne_paladins", "glacial_claymore")),
            "loot_n_explore"
        );

        createSmithingTransformRecipe(
            "glacial_mace_smithing",
            Registries.ITEM.get(netheriteMace),
            frostMonarchTemplate,
            frozenSoul,
            Registries.ITEM.get(Identifier.of("lne_paladins", "glacial_mace")),
            "loot_n_explore"
        );

        createSmithingTransformRecipe(
            "glacial_great_hammer_smithing",
            Registries.ITEM.get(netheriteGreatHammer),
            frostMonarchTemplate,
            frozenSoul,
            Registries.ITEM.get(Identifier.of("lne_paladins", "glacial_great_hammer")),
            "loot_n_explore"
        );

        createSmithingTransformRecipe(
            "glacial_shield_smithing",
            Registries.ITEM.get(netheriteKiteShield),
            frostMonarchTemplate,
            frozenSoul,
            Registries.ITEM.get(Identifier.of("lne_paladins", "glacial_shield")),
            "loot_n_explore"
        );
    }
}
