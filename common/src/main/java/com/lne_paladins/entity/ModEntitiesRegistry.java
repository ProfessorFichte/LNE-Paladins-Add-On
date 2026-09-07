package com.lne_paladins.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import static com.lne_paladins.LNE_Paladins_Mod.MOD_ID;

public class ModEntitiesRegistry {

    public static void registerEntities() {
        var id = Identifier.of(MOD_ID, "templars_sky_splitter");
        TemplarsSkySplitterProjectile.ENTITY_TYPE = Registry.register(
                Registries.ENTITY_TYPE,
                RegistryKey.of(RegistryKeys.ENTITY_TYPE, id),
                EntityType.Builder.<TemplarsSkySplitterProjectile>create(TemplarsSkySplitterProjectile::new, SpawnGroup.MISC)
                        .dimensions(0.6F, 0.6F)
                        .maxTrackingRange(4)
                        .trackingTickInterval(10)
                        .build(id.toString())
        );
    }
}
