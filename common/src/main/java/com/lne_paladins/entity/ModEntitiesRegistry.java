package com.lne_paladins.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.lne_paladins.LNE_Paladins_Mod.MOD_ID;

public class ModEntitiesRegistry {

    public static void registerEntities() {
        TemplarsSkySplitterProjectile.ENTITY_TYPE = Registry.register(
                Registries.ENTITY_TYPE,
                Identifier.of(MOD_ID, "templars_sky_splitter"),
                FabricEntityTypeBuilder.<TemplarsSkySplitterProjectile>create(SpawnGroup.MISC, TemplarsSkySplitterProjectile::new)
                        .dimensions(EntityDimensions.changing(0.6F, 0.6F))
                        .trackRangeBlocks(64)
                        .trackedUpdateRate(10)
                        .build()
        );
    }
}
