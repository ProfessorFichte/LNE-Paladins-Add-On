package com.lne_paladins.item;

import more_rpg_loot.item.Group;
import net.fabric_extras.shield_api.item.CustomShieldItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.Rarity;
import net.minecraft.util.Util;
import net.more_rpg_classes.custom.MrpgLibSpells;
import net.spell_engine.api.config.AttributeModifier;
import net.spell_engine.api.config.ShieldConfig;
import net.spell_engine.rpg_series.item.Equipment;
import net.spell_engine.rpg_series.item.Weapon;
import net.spell_engine.api.spell.SpellDataComponents;
import net.spell_engine.api.spell.container.SpellContainers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static com.lne_paladins.LNE_Paladins_Mod.MOD_ID;
import static com.lne_paladins.LNE_Paladins_Mod.tweaksConfig;

public class LNEShields {
    public static final class Entry {
        private final Identifier id;
        private final Supplier<Ingredient> repair;
        private final List<AttributeModifier> attributes;
        private String translatedName = "";
        private final int durability;
        public Rarity rarity = Rarity.RARE;
        public List<Identifier> spells = null;

        public Equipment.LootProperties lootProperties = Equipment.LootProperties.EMPTY;

        public Entry(Identifier id, Supplier<Ingredient> repair, List<AttributeModifier> attributes, int durability) {
            this.id = id;
            this.repair = repair;
            this.attributes = attributes;
            this.durability = durability;
        }

        public Identifier id() {
            return id;
        }

        public Supplier<Ingredient> repair() {
            return repair;
        }

        public List<AttributeModifier> attributes() {
            return attributes;
        }

        public int durability() {
            return durability;
        }

        public Entry spell(Identifier spellId) {
            spells = List.of(spellId);
            return this;
        }
        public Entry translatedName(String translatedName) {
            this.translatedName = translatedName;
            return this;
        }
        public String translatedName() {
            return translatedName;
        }
        public String translationKey() {
            return Util.createTranslationKey("item", id());
        }

    }

    public static final ArrayList<Entry> entries = new ArrayList<>();

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

    public static Entry shield(String name, Supplier<Ingredient> repair, List<AttributeModifier> attributes, int durability) {
        var entry = new Entry(Identifier.of(MOD_ID, name), repair, attributes, durability);
        entry.lootProperties = Equipment.LootProperties.of(5);
        entries.add(entry);
        return entry;
    }

    ///SPELL IDS
    public static Identifier elder_guardian_shield_spell = MrpgLibSpells.elder_guardian_shield.id();
    public static Identifier ender_dragon_shield_spell = MrpgLibSpells.ender_dragon_shield.id();
    public static Identifier glacial_shield_spell = MrpgLibSpells.glacial_shield.id();
    public static Identifier wither_shield_spell = MrpgLibSpells.wither_shield.id();

    private static final String GENERIC_ARMOR_TOUGHNESS = "minecraft:generic.armor_toughness";
    private static final String GENERIC_MAX_HEALTH = "generic.max_health";
    private static final String GENERIC_ATTACK_DAMAGE = "generic.attack_damage";
    private static final String GENERIC_ATTACK_SPEED = "generic.attack_speed";
    public static final String DAMAGE_REFLECT = "more_rpg_classes:damage_reflect_modifier";

    private static final int durability = 4032;


    public static void register(Map<String, ShieldConfig> configs) {
        if (!tweaksConfig.value.disable_special_lne_weapons) {
            shield("ender_dragon_shield",() -> Ingredient.ofItems(Items.AMETHYST_SHARD), List.of(
                    new AttributeModifier(GENERIC_ATTACK_SPEED,  0.05F,  EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new AttributeModifier(GENERIC_MAX_HEALTH,  6.0f,  EntityAttributeModifier.Operation.ADD_VALUE)
            ), durability)
                    .translatedName("Coral Reef Guardian")
                    .spell(ender_dragon_shield_spell);
            shield("elder_guardian_shield",() -> Ingredient.ofItems(Items.PRISMARINE_SHARD), List.of(
                    new AttributeModifier(DAMAGE_REFLECT,  0.35F,  EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new AttributeModifier(GENERIC_MAX_HEALTH,  6.0f,  EntityAttributeModifier.Operation.ADD_VALUE)
            ), durability)
                    .translatedName("Dragon Bulwark")
                    .spell(elder_guardian_shield_spell);
            shield("glacial_shield",() -> Ingredient.ofItems(Items.ICE), List.of(
                    new AttributeModifier(GENERIC_ARMOR_TOUGHNESS,  1.0F,  EntityAttributeModifier.Operation.ADD_VALUE),
                    new AttributeModifier(GENERIC_MAX_HEALTH,  6.0f,  EntityAttributeModifier.Operation.ADD_VALUE)
            ), durability)
                    .translatedName("Frozen Wall")
                    .spell(glacial_shield_spell);
            shield("wither_shield",() -> Ingredient.ofItems(Items.BONE), List.of(
                    new AttributeModifier(GENERIC_ATTACK_DAMAGE,  0.05F,  EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new AttributeModifier(GENERIC_MAX_HEALTH,  6.0f,  EntityAttributeModifier.Operation.ADD_VALUE)
            ), durability)
                    .translatedName("The Mouth of the Wither")
                    .spell(wither_shield_spell);
        }

        ArrayList<Item> shields = new ArrayList<>();
        for (var entry: entries) {
            var config = configs.get(entry.id.toString());
            if (config == null) {
                config = new ShieldConfig();
                config.durability = entry.durability;
                config.attributes = entry.attributes;
                configs.put(entry.id.toString(), config);
            }
            ArrayList<Pair<RegistryEntry<EntityAttribute>, EntityAttributeModifier>> shieldAttributes = new ArrayList<>();
            for (var modifier: Weapon.attributesFrom(config.attributes).modifiers()) {
                shieldAttributes.add(new Pair<>(modifier.attribute(), modifier.modifier()));
            }
            var settings = new Item.Settings().maxDamage(config.durability);
            var tier = entry.lootProperties.tier();
            if (tier >= 3) {
                settings.fireproof();
            }
            if (entry.rarity != Rarity.COMMON) {
                settings.rarity(entry.rarity);
            }
            if (entry.spells != null) {
                settings.component(SpellDataComponents.SPELL_CONTAINER, SpellContainers.forShield(entry.spells));
            }
            var shield = new CustomShieldItem(SoundEvents.ITEM_ARMOR_EQUIP_IRON, entry.repair, shieldAttributes, settings);
            Registry.register(Registries.ITEM, entry.id, shield);
            shields.add(shield);
        }

        ItemGroupEvents.modifyEntriesEvent(Group.RPG_LOOT_KEY).register((content) -> {
            for (var shield: shields) {
                content.add(shield);
            }
        });
    }
}
