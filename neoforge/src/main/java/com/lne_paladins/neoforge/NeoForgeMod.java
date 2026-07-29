package com.lne_paladins.neoforge;

import com.lne_paladins.LNE_Paladins_Mod;
import net.minecraft.registry.RegistryKeys;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(LNE_Paladins_Mod.MOD_ID)
public final class NeoForgeMod {
    public NeoForgeMod(IEventBus modBus) {
        LNE_Paladins_Mod.init();
        modBus.addListener(RegisterEvent.class, NeoForgeMod::register);
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
}
