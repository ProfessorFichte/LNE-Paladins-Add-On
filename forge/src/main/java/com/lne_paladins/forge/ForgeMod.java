package com.lne_paladins.forge;

import com.lne_paladins.LNE_Paladins_Mod;
import com.lne_paladins.forge.client.ForgeClientMod;
import com.lne_paladins.item.LNEShields;
import net.minecraft.registry.RegistryKeys;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.RegisterEvent;

@Mod(LNE_Paladins_Mod.MOD_ID)
public final class ForgeMod {
    @SuppressWarnings("removal")
    public ForgeMod() {
        LNE_Paladins_Mod.init();
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(EventPriority.NORMAL, false, RegisterEvent.class, ForgeMod::register);
        modBus.addListener(EventPriority.NORMAL, false, BuildCreativeModeTabContentsEvent.class,
                ForgeMod::buildTabContents);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            ForgeClientMod.register(modBus);
        }
    }

    /// Forge 47 unfreezes exactly one registry per `RegisterEvent` window, so registration is split
    /// by registry. Note that `registerItems()` is a no-op on Forge: it is gated on Loot &amp; Explore
    /// being loaded, and Loot &amp; Explore has no Forge build.
    public static void register(RegisterEvent event) {
        event.register(RegistryKeys.ENTITY_TYPE, reg -> LNE_Paladins_Mod.registerEntities());
        event.register(RegistryKeys.STATUS_EFFECT, reg -> LNE_Paladins_Mod.registerEffects());
        event.register(RegistryKeys.ITEM, reg -> LNE_Paladins_Mod.registerItems());
    }

    private static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        if (!event.getTabKey().equals(LNEShields.tabKey)) {
            return;
        }
        for (var shield : LNEShields.shields) {
            event.accept(() -> shield);
        }
    }
}
