package com.lne_paladins.forge;

import com.lne_paladins.LNE_Paladins_Mod;
import com.lne_paladins.compat.LootNExplore;
import com.lne_paladins.effect.LNE_PaladinsEffects;
import com.lne_paladins.entity.ModEntitiesRegistry;
import com.lne_paladins.forge.client.ForgeClientMod;
import com.lne_paladins.item.LNEShields;
import com.lne_paladins.item.WeaponRegister;
import net.minecraft.registry.RegistryKeys;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.RegisterEvent;
import net.spell_engine.Platform;
import net.spell_engine.api.effect.Effects;

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

    // Goes through the helper on purpose, on Forge 47.0-47.3 a plain Registry.register throws "Can not register to a locked registry".
    public static void register(RegisterEvent event) {
        event.register(RegistryKeys.ENTITY_TYPE, helper -> {
            ModEntitiesRegistry.entityTypesToRegister().forEach(helper::register);
            ModEntitiesRegistry.linkEntries();
        });

        event.register(RegistryKeys.STATUS_EFFECT, helper -> {
            LNE_PaladinsEffects.effectsToRegister(LNE_Paladins_Mod.effectConfig.value)
                    .forEach(helper::register);
            Effects.linkEntries(LNE_PaladinsEffects.entries);
            LNE_Paladins_Mod.effectConfig.save();
        });

        event.register(RegistryKeys.ITEM, helper -> {
            if (Platform.util().isModLoaded(LootNExplore.MOD_ID)) {
                LNE_Paladins_Mod.itemConfig.refresh();
                LNE_Paladins_Mod.shieldConfig.refresh();
                LNE_Paladins_Mod.effectConfig.refresh();
                LNEShields.itemsToRegister(LNE_Paladins_Mod.shieldConfig.value.shields)
                        .forEach(helper::register);
                WeaponRegister.itemsToRegister(LNE_Paladins_Mod.itemConfig.value.weapons)
                        .forEach(helper::register);
                LNE_Paladins_Mod.itemConfig.save();
                LNE_Paladins_Mod.shieldConfig.save();
            }
        });
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
