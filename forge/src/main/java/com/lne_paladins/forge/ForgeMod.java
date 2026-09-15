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

    /// Registration goes through the `RegisterHelper` that `RegisterEvent` hands out, NOT through
    /// `Registry.register`. Forge only clears the vanilla `NamespacedWrapper`'s lock from 47.4.0
    /// onward; on 47.0-47.3 and NeoForge 1.20.1 it stays locked even inside the correct window, so a
    /// plain `Registry.register` there throws `Can not register to a locked registry`. `mods.toml`
    /// declares `[47,)`, so those are supported configurations.
    ///
    /// The loops below duplicate what `common` runs on Fabric, on purpose - the whole workaround
    /// stays inside `forge/` and the Fabric path is untouched. Forge posts one event per registry, so
    /// each block is declared unconditionally and runs in exactly its own window. Measured window
    /// order on 1.20.1: `... attribute (4) -> mob_effect (5) -> item (7) -> entity_type (8) ...`.
    public static void register(RegisterEvent event) {
        event.register(RegistryKeys.ENTITY_TYPE, helper -> {
            ModEntitiesRegistry.entityTypesToRegister().forEach(helper::register);
            // The helper returns void where `Registry.register` returned the type, and both entity
            // renderers plus the projectile's own constructor read the static field.
            ModEntitiesRegistry.linkEntries();
        });

        event.register(RegistryKeys.STATUS_EFFECT, helper -> {
            // Mirrors `LNE_Paladins_Mod.registerEffects()`, trailing `save()` included. Note it does
            // NOT refresh the effect config first - upstream refreshes it from `registerItems()`,
            // which is the ITEM window; reproducing that faithfully keeps Forge and Fabric equal.
            LNE_PaladinsEffects.effectsToRegister(LNE_Paladins_Mod.effectConfig.value)
                    .forEach(helper::register);
            Effects.linkEntries(LNE_PaladinsEffects.entries);
            LNE_Paladins_Mod.effectConfig.save();
        });

        // Loot & Explore is Fabric-only, so this block registers nothing on Forge today - it mirrors
        // `LNE_Paladins_Mod.registerItems()` exactly so it stays correct if that ever changes.
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
