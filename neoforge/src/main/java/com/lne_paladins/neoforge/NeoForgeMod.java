package com.lne_paladins.neoforge;

import com.lne_paladins.LNE_Paladins_Mod;
import com.lne_paladins.item.LNEShields;
import net.minecraft.registry.RegistryKeys;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(LNE_Paladins_Mod.MOD_ID)
public final class NeoForgeMod {
    public NeoForgeMod(IEventBus modBus) {
        LNE_Paladins_Mod.init();
        modBus.addListener(RegisterEvent.class, NeoForgeMod::register);
        modBus.addListener(BuildCreativeModeTabContentsEvent.class, NeoForgeMod::buildTabContents);
    }
    public static void register(RegisterEvent event) {
        event.register(RegistryKeys.ENTITY_TYPE, reg -> {
            LNE_Paladins_Mod.registerEntities();
        });
        event.register(RegistryKeys.ITEM, reg -> {
            LNE_Paladins_Mod.registerItems();
        });
        event.register(RegistryKeys.STATUS_EFFECT, reg -> {
            LNE_Paladins_Mod.registerEffects();
        });
    }
    private static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        if (!ModList.get().isLoaded("loot_n_explore")) {
            return;
        }
        if (!event.getTabKey().equals(LNEShields.tabKey)) {
            return;
        }
        for (var shield : LNEShields.shields) {
            event.add(shield);
        }
    }
}
