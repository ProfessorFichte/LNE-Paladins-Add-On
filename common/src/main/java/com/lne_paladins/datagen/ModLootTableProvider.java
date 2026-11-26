package com.lne_paladins.datagen;

import com.mrpg_lib.compat.spell_engine.loot.SpecificSpellScrollPoolLootFunction;
import com.mrpg_lib.util.loot.ConditionalItemEntry;
import com.mrpg_lib.util.loot.ConditionalItemLootFunction;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.KilledByPlayerLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.condition.SurvivesExplosionLootCondition;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.EnchantWithLevelsLootFunction;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import static com.lne_paladins.LNE_Paladins_Mod.MOD_ID;

public abstract class ModLootTableProvider extends SimpleFabricLootTableProvider {
    private BiConsumer<RegistryKey<LootTable>, LootTable.Builder> exporter;

    protected ModLootTableProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup, LootContextType type) {
        super(output, registryLookup, type);
    }

    protected abstract void generate();

    @Override
    public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> exporter) {
        this.exporter = exporter;
        generate();
    }

    protected void table(String path, LootPool.Builder... pools) {
        var table = LootTable.builder();
        for (var pool : pools) table.pool(pool);
        exporter.accept(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(MOD_ID, path)), table);
    }

    protected static LootPool.Builder pool(LootNumberProvider rolls, LootPoolEntry.Builder<?>... entries) {
        var pool = LootPool.builder().rolls(rolls);
        for (var entry : entries) pool.with(entry);
        return pool;
    }

    protected static LootNumberProvider constant(float value) {
        return ConstantLootNumberProvider.create(value);
    }

    protected static LootNumberProvider uniform(float min, float max) {
        return UniformLootNumberProvider.create(min, max);
    }

    protected static LeafEntry.Builder<?> item(String id, int weight, LootFunction.Builder... functions) {
        Item item = Registries.ITEM.get(Identifier.of(id));
        if (item == Items.AIR) throw new IllegalStateException("Loot table item is not registered: " + id);
        var entry = ItemEntry.builder(item).weight(weight);
        for (var function : functions) entry.apply(function);
        return entry;
    }

    protected static LeafEntry.Builder<?> conditional(String id, int weight, LootFunction.Builder... functions) {
        var entry = ConditionalItemEntry.builder(Identifier.of(id), Optional.empty()).weight(weight);
        for (var function : functions) entry.apply(function);
        return entry;
    }

    protected static LeafEntry.Builder<?> empty(int weight) {
        return EmptyEntry.builder().weight(weight);
    }

    protected static LeafEntry.Builder<?> nested(String id, int weight) {
        return LootTableEntry.builder(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(id))).weight(weight);
    }

    protected static ConditionalLootFunction.Builder<?> count(LootNumberProvider count) {
        return SetCountLootFunction.builder(count);
    }

    protected static EnchantWithLevelsLootFunction.Builder enchant(float min, float max) {
        return new EnchantWithLevelsLootFunction.Builder(uniform(min, max));
    }

    protected static LootFunction.Builder scroll(int tierMin, int tierMax, int count, String... pools) {
        return SpecificSpellScrollPoolLootFunction.builder(List.of(pools), constant(tierMin), constant(tierMax), constant(count), List.of());
    }

    protected static LootFunction.Builder conditionalItem(String id) {
        return ConditionalItemLootFunction.builder(Identifier.of(id));
    }

    protected static LootCondition.Builder chance(float chance) {
        return RandomChanceLootCondition.builder(chance);
    }

    protected static LootCondition.Builder killedByPlayer() {
        return KilledByPlayerLootCondition.builder();
    }

    protected static LootCondition.Builder survivesExplosion() {
        return SurvivesExplosionLootCondition.builder();
    }

    public static class Chests extends ModLootTableProvider {
        public Chests(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(output, registryLookup, LootContextTypes.CHEST);
        }

        @Override
        protected void generate() {
            chestsGeneral0();
            chestsGeneral1();
            chestsPaladinsTier1();
            chestsPaladinsTier2();
            chestsPaladinsTier3();
            chestsPaladinsTier4();
            chestsPaladinsTier5();
            chestsPriestsTier1();
            chestsPriestsTier2();
            chestsPriestsTier3();
            chestsPriestsTier4();
            chestsPriestsTier5();
            chestsTier1();
            chestsTier2();
            chestsTier3();
            chestsTier4();
            chestsTier5();
        }

        private void chestsGeneral0() {
            table("chests/general_0",
                pool(uniform(2, 4),
                        item("minecraft:gold_nugget", 1, count(uniform(3, 8))),
                        item("minecraft:paper", 1, count(uniform(1, 4))),
                        item("minecraft:white_wool", 1, count(uniform(1, 4))),
                        item("minecraft:honeycomb", 1, count(uniform(1, 2))),
                        item("minecraft:iron_nugget", 1, count(uniform(3, 8)))));
        }

        private void chestsGeneral1() {
            table("chests/general_1",
                pool(uniform(2, 4),
                        item("minecraft:bread", 1, count(uniform(1, 4))),
                        item("minecraft:wheat", 1, count(uniform(3, 7))),
                        item("minecraft:cooked_porkchop", 1, count(uniform(1, 2))),
                        item("minecraft:gold_nugget", 1, count(uniform(2, 5))),
                        item("minecraft:apple", 1, count(uniform(1, 3))),
                        item("minecraft:lapis_lazuli", 1, count(uniform(1, 2))),
                        item("runes:healing_stone", 1, count(uniform(1, 10)))));
        }

        private void chestsPaladinsTier1() {
            table("chests/paladins/tier1",
                pool(uniform(1, 2),
                        item("minecraft:stick", 5, count(uniform(2, 4))),
                        item("minecraft:gold_nugget", 4, count(uniform(2, 6))),
                        item("minecraft:lapis_lazuli", 3, count(uniform(1, 2))),
                        item("minecraft:honeycomb", 3, count(uniform(1, 3))),
                        item("minecraft:iron_ingot", 2, count(uniform(1, 2))),
                        item("runes:healing_stone", 2, count(uniform(1, 8))),
                        item("minecraft:copper_ingot", 2, count(uniform(1, 2))),
                        item("minecraft:gold_ingot", 2, count(uniform(1, 2))),
                        item("paladins:stone_great_hammer", 3),
                        item("paladins:stone_claymore", 3),
                        item("paladins:iron_claymore", 1),
                        item("paladins:iron_great_hammer", 1),
                        item("paladins:iron_mace", 1),
                        item("paladins:iron_kite_shield", 1)),
                pool(uniform(1, 1),
                        empty(7),
                        item("spell_engine:spell_scroll", 1, scroll(2, 2, 1, "#paladins:spell_scroll/paladin"))),
                pool(uniform(1, 1),
                        empty(15),
                        conditional("jewelry:sapphire_ring", 1),
                        conditional("jewelry:sapphire_necklace", 1),
                        conditional("jewelry:citrine_ring", 1),
                        conditional("jewelry:citrine_necklace", 1),
                        conditional("relics_rpgs:jewel_figurine_citrine", 1),
                        conditional("relics_rpgs:jewel_figurine_sapphire", 1),
                        conditional("loot_n_explore:sweet_berry_punch", 1)));
        }

        private void chestsPaladinsTier2() {
            table("chests/paladins/tier2",
                pool(uniform(1, 3),
                        item("minecraft:iron_ingot", 3, count(uniform(2, 5))),
                        item("minecraft:gold_ingot", 3, count(uniform(2, 5))),
                        item("minecraft:copper_ingot", 3, count(uniform(2, 5))),
                        item("runes:healing_stone", 3, count(uniform(1, 10))),
                        item("minecraft:chain", 3, count(uniform(2, 4))),
                        item("minecraft:honeycomb", 3, count(uniform(2, 5))),
                        item("minecraft:ghast_tear", 2, count(uniform(1, 2))),
                        item("paladins:iron_mace", 2),
                        item("paladins:iron_claymore", 2),
                        item("paladins:iron_great_hammer", 2),
                        item("paladins:iron_kite_shield", 2),
                        item("paladins:paladin_armor_head", 1),
                        item("paladins:paladin_armor_chest", 1),
                        item("paladins:paladin_armor_legs", 1),
                        item("paladins:paladin_armor_feet", 1)),
                pool(uniform(1, 1),
                        empty(5),
                        item("spell_engine:spell_scroll", 1, scroll(2, 2, 1, "#paladins:spell_scroll/paladin"))),
                pool(uniform(1, 1),
                        empty(10),
                        conditional("jewelry:sapphire_ring", 1),
                        conditional("jewelry:sapphire_necklace", 1),
                        conditional("jewelry:citrine_ring", 1),
                        conditional("jewelry:citrine_necklace", 1),
                        conditional("relics_rpgs:jewel_figurine_citrine", 1),
                        conditional("relics_rpgs:jewel_figurine_sapphire", 1),
                        conditional("loot_n_explore:sweet_berry_punch", 1),
                        conditional("loot_n_explore:potato_soup", 1)));
        }

        private void chestsPaladinsTier3() {
            table("chests/paladins/tier3",
                pool(uniform(2, 3),
                        item("minecraft:copper_ingot", 4, count(uniform(1, 4))),
                        item("minecraft:gold_ingot", 4, count(uniform(1, 4))),
                        item("minecraft:iron_ingot", 4, count(uniform(1, 4))),
                        item("runes:healing_stone", 4, count(uniform(6, 16))),
                        item("minecraft:honeycomb", 4, count(uniform(2, 5))),
                        item("minecraft:ghast_tear", 4, count(uniform(2, 3))),
                        item("paladins:iron_mace", 3, enchant(1, 13).conditionally(chance(0.35F))),
                        item("paladins:iron_claymore", 3, enchant(1, 13).conditionally(chance(0.35F))),
                        item("paladins:iron_great_hammer", 3, enchant(1, 13).conditionally(chance(0.35F))),
                        item("paladins:iron_kite_shield", 3, enchant(1, 13).conditionally(chance(0.35F))),
                        item("paladins:paladin_armor_head", 2, enchant(1, 13).conditionally(chance(0.35F))),
                        item("paladins:paladin_armor_chest", 2, enchant(1, 13).conditionally(chance(0.35F))),
                        item("paladins:paladin_armor_legs", 2, enchant(1, 13).conditionally(chance(0.35F))),
                        item("paladins:paladin_armor_feet", 2, enchant(1, 13).conditionally(chance(0.35F))),
                        item("paladins:diamond_mace", 1, enchant(1, 13).conditionally(chance(0.35F))),
                        item("paladins:diamond_claymore", 1, enchant(1, 13).conditionally(chance(0.35F))),
                        item("paladins:diamond_great_hammer", 1, enchant(1, 13).conditionally(chance(0.35F))),
                        item("paladins:diamond_kite_shield", 1, enchant(1, 13).conditionally(chance(0.35F))),
                        item("paladins:crusader_armor_head", 1, enchant(1, 13).conditionally(chance(0.35F))),
                        item("paladins:crusader_armor_chest", 1, enchant(1, 13).conditionally(chance(0.35F))),
                        item("paladins:crusader_armor_legs", 1, enchant(1, 13).conditionally(chance(0.35F))),
                        item("paladins:crusader_armor_feet", 1, enchant(1, 13).conditionally(chance(0.35F)))).bonusRolls(constant(0.25F)),
                pool(uniform(1, 1),
                        empty(3),
                        item("spell_engine:spell_scroll", 1, scroll(2, 3, 1, "#paladins:spell_scroll/paladin"))),
                pool(uniform(1, 1),
                        empty(8),
                        conditional("jewelry:sapphire_ring", 1),
                        conditional("jewelry:sapphire_necklace", 1),
                        conditional("jewelry:citrine_ring", 1),
                        conditional("jewelry:citrine_necklace", 1),
                        conditional("relics_rpgs:lesser_use_spell_power", 1),
                        conditional("relics_rpgs:lesser_use_health", 1),
                        conditional("relics_rpgs:lesser_melee_crit_chance", 1),
                        conditional("loot_n_explore:malt_extract", 1),
                        conditional("loot_n_explore:beet_rootbeer", 1),
                        conditional("loot_n_explore:holy_water", 1)));
        }

        private void chestsPaladinsTier4() {
            table("chests/paladins/tier4",
                pool(uniform(2, 4),
                        item("minecraft:iron_ingot", 3, count(uniform(3, 7))),
                        item("minecraft:gold_ingot", 3, count(uniform(3, 7))),
                        item("minecraft:copper_ingot", 3, count(uniform(3, 7))),
                        item("runes:healing_stone", 4, count(uniform(10, 20))),
                        item("minecraft:ghast_tear", 3, count(uniform(2, 3))),
                        item("minecraft:diamond", 2, count(uniform(1, 4))),
                        item("paladins:diamond_mace", 1, enchant(6, 18).conditionally(chance(0.5F))),
                        item("paladins:diamond_claymore", 1, enchant(6, 18).conditionally(chance(0.5F))),
                        item("paladins:diamond_great_hammer", 1, enchant(6, 18).conditionally(chance(0.5F))),
                        item("paladins:diamond_kite_shield", 1, enchant(6, 18).conditionally(chance(0.5F))),
                        item("paladins:crusader_armor_head", 1, enchant(6, 18).conditionally(chance(0.5F))),
                        item("paladins:crusader_armor_chest", 1, enchant(6, 18).conditionally(chance(0.5F))),
                        item("paladins:crusader_armor_legs", 1, enchant(6, 18).conditionally(chance(0.5F))),
                        item("paladins:crusader_armor_feet", 1, enchant(6, 18).conditionally(chance(0.5F)))).bonusRolls(constant(0.5F)),
                pool(uniform(1, 1),
                        empty(1),
                        item("spell_engine:spell_scroll", 1, scroll(3, 5, 1, "#paladins:spell_scroll/paladin"))),
                pool(uniform(1, 1),
                        empty(3),
                        conditional("jewelry:netherite_sapphire_ring", 1),
                        conditional("jewelry:netherite_sapphire_necklace", 1),
                        conditional("jewelry:netherite_citrine_ring", 1),
                        conditional("jewelry:netherite_citrine_necklace", 1),
                        conditional("relics_rpgs:medium_proc_attack_damage", 1),
                        conditional("relics_rpgs:medium_proc_defense", 1),
                        conditional("relics_rpgs:medium_proc_healing_power", 1),
                        conditional("loot_n_explore:vital_drink", 1),
                        conditional("loot_n_explore:holy_water", 1)));
        }

        private void chestsPaladinsTier5() {
            table("chests/paladins/tier5",
                pool(uniform(2, 4),
                        item("runes:healing_stone", 3, count(uniform(15, 26))),
                        item("minecraft:iron_ingot", 3, count(uniform(3, 7))),
                        item("minecraft:gold_ingot", 3, count(uniform(3, 7))),
                        item("minecraft:copper_ingot", 3, count(uniform(3, 7))),
                        item("minecraft:diamond", 2, count(uniform(3, 6))),
                        item("minecraft:ghast_tear", 2, count(uniform(3, 4))),
                        item("minecraft:golden_apple", 1),
                        item("paladins:diamond_mace", 1, enchant(13, 24).conditionally(chance(0.75F))),
                        item("paladins:diamond_great_hammer", 1, enchant(13, 24).conditionally(chance(0.75F))),
                        item("paladins:diamond_claymore", 1, enchant(13, 24).conditionally(chance(0.75F))),
                        item("paladins:diamond_kite_shield", 1, enchant(13, 24).conditionally(chance(0.75F))),
                        item("paladins:crusader_armor_head", 1, enchant(13, 24).conditionally(chance(0.75F))),
                        item("paladins:crusader_armor_chest", 1, enchant(13, 24).conditionally(chance(0.75F))),
                        item("paladins:crusader_armor_legs", 1, enchant(13, 24).conditionally(chance(0.75F))),
                        item("paladins:crusader_armor_feet", 1, enchant(13, 24).conditionally(chance(0.75F)))).bonusRolls(constant(0.75F)),
                pool(uniform(1, 1),
                        item("spell_engine:spell_scroll", 1, scroll(4, 5, 1, "#paladins:spell_scroll/paladin"))),
                pool(uniform(1, 1),
                        conditional("jewelry:netherite_sapphire_ring", 1),
                        conditional("jewelry:netherite_sapphire_necklace", 1),
                        conditional("jewelry:netherite_citrine_ring", 1),
                        conditional("jewelry:netherite_citrine_necklace", 1),
                        conditional("relics_rpgs:greater_perk_melee_stun", 1),
                        conditional("relics_rpgs:greater_perk_defense_block", 1),
                        conditional("relics_rpgs:greater_perk_shield_reset", 1),
                        conditional("relics_rpgs:greater_proc_defense_danger", 1),
                        conditional("loot_n_explore:vital_drink", 1),
                        conditional("loot_n_explore:holy_water", 1)));
        }

        private void chestsPriestsTier1() {
            table("chests/priests/tier1",
                pool(uniform(1, 2),
                        item("minecraft:stick", 5, count(uniform(2, 4))),
                        item("minecraft:chain", 5, count(uniform(4, 8))),
                        item("minecraft:white_wool", 5, count(uniform(2, 6))),
                        item("minecraft:gold_nugget", 4, count(uniform(2, 6))),
                        item("minecraft:lapis_lazuli", 3, count(uniform(1, 2))),
                        item("minecraft:honeycomb", 3, count(uniform(1, 3))),
                        item("runes:healing_stone", 2, count(uniform(1, 8))),
                        item("minecraft:gold_ingot", 2, count(uniform(1, 2))),
                        item("paladins:acolyte_wand", 3),
                        item("paladins:holy_wand", 1)),
                pool(uniform(1, 1),
                        empty(7),
                        item("spell_engine:spell_scroll", 1, scroll(2, 2, 1, "#paladins:spell_scroll/priest"))),
                pool(uniform(1, 1),
                        empty(15),
                        conditional("jewelry:citrine_ring", 1),
                        conditional("jewelry:citrine_necklace", 1),
                        conditional("relics_rpgs:jewel_figurine_citrine", 1),
                        conditional("loot_n_explore:orange_juice", 1)));
        }

        private void chestsPriestsTier2() {
            table("chests/priests/tier2",
                pool(uniform(1, 3),
                        item("minecraft:chain", 3, count(uniform(4, 8))),
                        item("minecraft:gold_ingot", 3, count(uniform(2, 5))),
                        item("runes:healing_stone", 3, count(uniform(1, 10))),
                        item("minecraft:chain", 3, count(uniform(2, 4))),
                        item("minecraft:honeycomb", 3, count(uniform(2, 5))),
                        item("minecraft:ghast_tear", 2, count(uniform(1, 2))),
                        item("paladins:holy_wand", 2),
                        item("paladins:priest_robe_head", 1),
                        item("paladins:priest_robe_chest", 1),
                        item("paladins:priest_robe_legs", 1),
                        item("paladins:priest_robe_feet", 1)),
                pool(uniform(1, 1),
                        empty(5),
                        item("spell_engine:spell_scroll", 1, scroll(2, 2, 1, "#paladins:spell_scroll/priest"))),
                pool(uniform(1, 1),
                        empty(10),
                        conditional("jewelry:citrine_ring", 1),
                        conditional("jewelry:citrine_necklace", 1),
                        conditional("relics_rpgs:jewel_figurine_citrine", 1),
                        conditional("loot_n_explore:orange_juice", 1)));
        }

        private void chestsPriestsTier3() {
            table("chests/priests/tier3",
                pool(uniform(2, 3),
                        item("minecraft:chain", 4, count(uniform(1, 4))),
                        item("minecraft:gold_ingot", 4, count(uniform(1, 4))),
                        item("runes:healing_stone", 4, count(uniform(6, 16))),
                        item("minecraft:honeycomb", 4, count(uniform(2, 5))),
                        item("minecraft:ghast_tear", 4, count(uniform(2, 3))),
                        item("paladins:holy_wand", 3, enchant(1, 13).conditionally(chance(0.35F))),
                        item("paladins:holy_staff", 3, enchant(1, 13).conditionally(chance(0.35F))),
                        item("paladins:priest_robe_head", 2, enchant(1, 13).conditionally(chance(0.35F))),
                        item("paladins:priest_robe_chest", 2, enchant(1, 13).conditionally(chance(0.35F))),
                        item("paladins:priest_robe_legs", 2, enchant(1, 13).conditionally(chance(0.35F))),
                        item("paladins:priest_robe_feet", 2, enchant(1, 13).conditionally(chance(0.35F))),
                        item("paladins:diamond_holy_wand", 1, enchant(1, 13).conditionally(chance(0.35F))),
                        item("paladins:prior_robe_head", 1, enchant(1, 13).conditionally(chance(0.35F))),
                        item("paladins:prior_robe_chest", 1, enchant(1, 13).conditionally(chance(0.35F))),
                        item("paladins:prior_robe_legs", 1, enchant(1, 13).conditionally(chance(0.35F))),
                        item("paladins:prior_robe_feet", 1, enchant(1, 13).conditionally(chance(0.35F)))).bonusRolls(constant(0.25F)),
                pool(uniform(1, 1),
                        empty(3),
                        item("spell_engine:spell_scroll", 1, scroll(2, 3, 1, "#paladins:spell_scroll/priest"))),
                pool(uniform(1, 1),
                        empty(8),
                        conditional("jewelry:citrine_ring", 1),
                        conditional("jewelry:citrine_necklace", 1),
                        conditional("relics_rpgs:lesser_use_spell_power", 1),
                        conditional("relics_rpgs:lesser_use_spell_haste", 1),
                        conditional("relics_rpgs:lesser_proc_crit_damage", 1),
                        conditional("relics_rpgs:lesser_proc_frost_healing", 1),
                        conditional("loot_n_explore:sweet_chilli", 1),
                        conditional("loot_n_explore:holy_water", 1)));
        }

        private void chestsPriestsTier4() {
            table("chests/priests/tier4",
                pool(uniform(2, 4),
                        item("minecraft:chain", 3, count(uniform(3, 5))),
                        item("minecraft:gold_ingot", 3, count(uniform(3, 7))),
                        item("runes:healing_stone", 4, count(uniform(10, 20))),
                        item("minecraft:ghast_tear", 3, count(uniform(2, 3))),
                        item("minecraft:diamond", 2, count(uniform(1, 4))),
                        item("paladins:diamond_holy_staff", 1, enchant(6, 18).conditionally(chance(0.5F))),
                        item("paladins:prior_robe_head", 1, enchant(6, 18).conditionally(chance(0.5F))),
                        item("paladins:prior_robe_chest", 1, enchant(6, 18).conditionally(chance(0.5F))),
                        item("paladins:prior_robe_legs", 1, enchant(6, 18).conditionally(chance(0.5F))),
                        item("paladins:prior_robe_feet", 1, enchant(6, 18).conditionally(chance(0.5F)))).bonusRolls(constant(0.5F)),
                pool(uniform(1, 1),
                        empty(1),
                        item("spell_engine:spell_scroll", 1, scroll(3, 5, 1, "#paladins:spell_scroll/priest"))),
                pool(uniform(1, 1),
                        empty(3),
                        conditional("jewelry:netherite_citrine_ring", 1),
                        conditional("jewelry:netherite_citrine_necklace", 1),
                        conditional("relics_rpgs:medium_proc_spell_power", 1),
                        conditional("relics_rpgs:medium_proc_spell_haste", 1),
                        conditional("relics_rpgs:medium_proc_healing_power", 1),
                        conditional("loot_n_explore:holy_water", 1)));
        }

        private void chestsPriestsTier5() {
            table("chests/priests/tier5",
                pool(uniform(2, 4),
                        item("runes:healing_stone", 3, count(uniform(15, 26))),
                        item("minecraft:gold_ingot", 3, count(uniform(3, 7))),
                        item("minecraft:diamond", 2, count(uniform(3, 6))),
                        item("minecraft:ghast_tear", 2, count(uniform(3, 4))),
                        item("minecraft:golden_apple", 1),
                        item("paladins:diamond_holy_staff", 1, enchant(13, 24).conditionally(chance(0.75F))),
                        item("paladins:prior_robe_head", 1, enchant(13, 24).conditionally(chance(0.75F))),
                        item("paladins:prior_robe_chest", 1, enchant(13, 24).conditionally(chance(0.75F))),
                        item("paladins:prior_robe_legs", 1, enchant(13, 24).conditionally(chance(0.75F))),
                        item("paladins:prior_robe_feet", 1, enchant(13, 24).conditionally(chance(0.75F)))).bonusRolls(constant(0.75F)),
                pool(uniform(1, 1),
                        item("spell_engine:spell_scroll", 1, scroll(4, 5, 1, "#paladins:spell_scroll/priest"))),
                pool(uniform(1, 1),
                        conditional("jewelry:netherite_citrine_ring", 1),
                        conditional("jewelry:netherite_citrine_necklace", 1),
                        conditional("relics_rpgs:greater_perk_heal_cleanse", 1),
                        conditional("relics_rpgs:greater_perk_heal_danger", 1),
                        conditional("loot_n_explore:holy_water", 1)));
        }

        private void chestsTier1() {
            table("chests/tier1",
                pool(constant(1),
                        nested("lne_paladins:chests/paladins/tier1", 1),
                        nested("lne_paladins:chests/priests/tier1", 1)));
        }

        private void chestsTier2() {
            table("chests/tier2",
                pool(constant(1),
                        nested("lne_paladins:chests/paladins/tier2", 1),
                        nested("lne_paladins:chests/priests/tier2", 1)));
        }

        private void chestsTier3() {
            table("chests/tier3",
                pool(constant(1),
                        nested("lne_paladins:chests/paladins/tier3", 1),
                        nested("lne_paladins:chests/priests/tier3", 1)));
        }

        private void chestsTier4() {
            table("chests/tier4",
                pool(constant(1),
                        nested("lne_paladins:chests/paladins/tier4", 1),
                        nested("lne_paladins:chests/priests/tier4", 1)));
        }

        private void chestsTier5() {
            table("chests/tier5",
                pool(constant(1),
                        nested("lne_paladins:chests/paladins/tier5", 1),
                        nested("lne_paladins:chests/priests/tier5", 1)));
        }
    }
}
