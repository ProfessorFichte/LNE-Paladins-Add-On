package com.lne_paladins.fabric;

import com.lne_paladins.LNE_Paladins_Mod;
import net.fabricmc.api.ModInitializer;

public final class FabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        LNE_Paladins_Mod.init();
        LNE_Paladins_Mod.registerEntities();
        LNE_Paladins_Mod.registerEffects();
        LNE_Paladins_Mod.registerItems();
    }
}
