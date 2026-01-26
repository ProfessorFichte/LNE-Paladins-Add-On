package com.lne_paladins.datagen;

import com.lne_paladins.spells.PaladinsSpells;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper;
import net.spell_engine.api.datagen.SpellGenerator;

import java.util.concurrent.CompletableFuture;

public class PaladinsAbilityDatagen extends SpellGenerator {
    public PaladinsAbilityDatagen(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateSpells(Builder builder) {
        // Register all spells from the PaladinsSpells entries list
        for (var entry : PaladinsSpells.entries) {
            builder.add(entry.id(), entry.spell());
        }
    }
}
