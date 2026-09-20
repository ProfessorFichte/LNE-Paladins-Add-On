package com.lne_paladins.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.Map;

import static com.lne_paladins.LNE_Paladins_Mod.MOD_ID;

public class ModEntitiesRegistry {
    public static final Identifier TEMPLARS_SKY_SPLITTER_ID = new Identifier(MOD_ID, "templars_sky_splitter");

    private static EntityType<TemplarsSkySplitterProjectile> createTemplarsSkySplitter() {
        return EntityType.Builder.<TemplarsSkySplitterProjectile>create(TemplarsSkySplitterProjectile::new, SpawnGroup.MISC)
                .setDimensions(0.6F, 0.6F)
                .maxTrackingRange(4)
                .trackingTickInterval(10)
                .build(TEMPLARS_SKY_SPLITTER_ID.toString());
    }

    public static Map<Identifier, EntityType<?>> entityTypesToRegister() {
        return Map.of(TEMPLARS_SKY_SPLITTER_ID, createTemplarsSkySplitter());
    }

    @SuppressWarnings("unchecked")
    public static void linkEntries() {
        TemplarsSkySplitterProjectile.ENTITY_TYPE = (EntityType<TemplarsSkySplitterProjectile>) Registries.ENTITY_TYPE
                .getOrEmpty(TEMPLARS_SKY_SPLITTER_ID)
                .orElseThrow(() -> new IllegalStateException(
                        TEMPLARS_SKY_SPLITTER_ID + " is not in the entity type registry - register it first"));
    }

    public static void registerEntities() {
        TemplarsSkySplitterProjectile.ENTITY_TYPE = Registry.register(
                Registries.ENTITY_TYPE,
                RegistryKey.of(RegistryKeys.ENTITY_TYPE, TEMPLARS_SKY_SPLITTER_ID),
                createTemplarsSkySplitter()
        );
    }
}
