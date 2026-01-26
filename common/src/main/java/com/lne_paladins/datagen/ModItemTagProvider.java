package com.lne_paladins.datagen;

import com.lne_paladins.item.LNEShields;
import com.lne_paladins.item.WeaponRegister;
import more_rpg_loot.compat.spell_engine.LNE_Weapons;
import more_rpg_loot.util.LneItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.spell_engine.rpg_series.datagen.RPGSeriesDataGen;
import net.spell_engine.rpg_series.tags.RPGSeriesItemTags;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ModItemTagProvider extends RPGSeriesDataGen.ItemTagGenerator {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    public void generateShieldTags(List<RPGSeriesDataGen.ShieldEntry> shields) {
        Iterator var2 = shields.iterator();

        while(var2.hasNext()) {
            RPGSeriesDataGen.ShieldEntry entry = (RPGSeriesDataGen.ShieldEntry)var2.next();
            Identifier id = entry.id();
            TagKey<Item> weaponType = RPGSeriesItemTags.WeaponType.get(net.spell_engine.api.item.Equipment.WeaponType.SHIELD);
            FabricTagProvider<Item>.FabricTagBuilder weaponTag = this.getOrCreateTagBuilder(weaponType);
            weaponTag.addOptional(id);
        }
    }
    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        // Add weapons to RPG Series tags
        for (var entry : WeaponRegister.entries) {
            var weaponType = RPGSeriesItemTags.WeaponType.get(entry.category());
            getOrCreateTagBuilder(weaponType).addOptional(entry.id());

            String lootTheme = entry.lootProperties().theme();
            if (lootTheme != null && !lootTheme.isEmpty()) {
                getOrCreateTagBuilder(RPGSeriesItemTags.LootThemes.get(lootTheme))
                    .addOptional(entry.id());
            }
        }
        var shieldEntries = LNEShields.entries.stream().map(entry ->
                new RPGSeriesDataGen.ShieldEntry(entry.id(), entry.lootProperties)
        ).toList();
        generateShieldTags(shieldEntries);

       ///WAIT FOR LNE UPDATE THAN ENABLE THIS

        /*
        for (var entry : WeaponRegister.entries) {
            String name = entry.id().getPath();
            if (name.contains("ender_dragon")) {
                getOrCreateTagBuilder(LneItemTags.WEAPON_THEME_ENDER_DRAGON)
                        .addOptional(entry.id());
            } else if (name.contains("wither")) {
                getOrCreateTagBuilder(LneItemTags.WEAPON_THEME_WITHER)
                        .addOptional(entry.id());
            } else if (name.contains("glacial")) {
                getOrCreateTagBuilder(LneItemTags.WEAPON_THEME_GLACIAL)
                        .addOptional(entry.id());
            } else if (name.contains("elder_guardian")) {
                getOrCreateTagBuilder(LneItemTags.WEAPON_THEME_ELDER_GUARDIAN)
                        .addOptional(entry.id());
            }
        }
        for (var entry : LNEShields.entries) {
            String name = entry.id().getPath();
            if (name.contains("ender_dragon")) {
                getOrCreateTagBuilder(LneItemTags.WEAPON_THEME_ENDER_DRAGON)
                        .addOptional(entry.id());
            } else if (name.contains("wither")) {
                getOrCreateTagBuilder(LneItemTags.WEAPON_THEME_WITHER)
                        .addOptional(entry.id());
            } else if (name.contains("glacial")) {
                getOrCreateTagBuilder(LneItemTags.WEAPON_THEME_GLACIAL)
                        .addOptional(entry.id());
            } else if (name.contains("elder_guardian")) {
                getOrCreateTagBuilder(LneItemTags.WEAPON_THEME_ELDER_GUARDIAN)
                        .addOptional(entry.id());
            }
        }
         */
    }
}
