package com.lne_paladins.datagen;

import com.lne_paladins.effect.LNE_PaladinsEffects;
import com.lne_paladins.item.LNEShields;
import com.lne_paladins.item.WeaponRegister;
import com.lne_paladins.spells.LNE_PaladinsSpells;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class ModLanguageProvider extends FabricLanguageProvider {

    public ModLanguageProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder builder) {

        // STATUS EFFECTS
        LNE_PaladinsEffects.entries.forEach(entry -> {
            builder.add(entry.effect.getTranslationKey(), entry.title);
            builder.add(entry.effect.getTranslationKey() + ".description", entry.description);
        });

        for (var entry : LNE_PaladinsSpells.entries) {
            var id = entry.id();
                builder.add("spell." + id.getNamespace() + "." + id.getPath() + ".name", entry.title());
                builder.add("spell." + id.getNamespace() + "." + id.getPath() + ".description", entry.description());
            }
        // WEAPONS
        LNEShields.entries.forEach(entry ->
                builder.add(entry.translationKey(), entry.translatedName())
        );
        WeaponRegister.entries.forEach(entry ->
                builder.add(entry.item().getTranslationKey(), entry.translatedName())
        );
    }
}
