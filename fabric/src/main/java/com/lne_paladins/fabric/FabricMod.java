package com.lne_paladins.fabric;

import com.lne_paladins.LNE_Paladins_Mod;
import com.lne_paladins.item.LNEShields;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;

public final class FabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        LNE_Paladins_Mod.init();
        LNE_Paladins_Mod.registerEntities();
        LNE_Paladins_Mod.registerEffects();
        LNE_Paladins_Mod.registerItems();

        if (FabricLoader.getInstance().isModLoaded("loot_n_explore")) {
            ItemGroupEvents.modifyEntriesEvent(LNEShields.tabKey).register((content) -> {
                for (var shield : LNEShields.shields) {
                    content.add(shield);
                }
            });
        }
    }
}
