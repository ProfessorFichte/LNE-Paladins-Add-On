package com.lne_paladins.datagen;

import com.google.gson.JsonObject;
import com.lne_paladins.item.WeaponRegister;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.registry.RegistryWrapper;
import net.spell_engine.api.item.weapon.Weapon;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class WeaponAttributesGenerator implements DataProvider {
    private final DataOutput.PathResolver pathResolver;

    public WeaponAttributesGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        this.pathResolver = output.getResolver(DataOutput.OutputType.DATA_PACK, "weapon_attributes");
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        for (Weapon.Entry entry : WeaponRegister.entries) {
            if (entry.id().getPath().contains("mace")) {
                JsonObject json = new JsonObject();
                json.addProperty("parent", "bettercombat" + ":mace");
                Path path = pathResolver.resolveJson(entry.id());
                futures.add(DataProvider.writeToPath(writer, json, path));
            }
            if (entry.id().getPath().contains("hammer")) {
                JsonObject json = new JsonObject();
                json.addProperty("parent", "bettercombat" + ":hammer");
                Path path = pathResolver.resolveJson(entry.id());
                futures.add(DataProvider.writeToPath(writer, json, path));
            }
            if (entry.id().getPath().contains("claymore")) {
                JsonObject json = new JsonObject();
                json.addProperty("parent", "bettercombat" + ":claymore");
                Path path = pathResolver.resolveJson(entry.id());
                futures.add(DataProvider.writeToPath(writer, json, path));
            }
            if (entry.id().getPath().contains("staff")) {
                JsonObject json = new JsonObject();
                json.addProperty("parent", "bettercombat" + ":staff");
                Path path = pathResolver.resolveJson(entry.id());
                futures.add(DataProvider.writeToPath(writer, json, path));
            }
        }

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "LNE Paladins & Priests Weapon Attributes";
    }
}
