package com.lne_paladins.compat;

import net.minecraft.item.ItemGroup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

/// Loot &amp; Explore touch points, named by identifier only.
///
/// Loot &amp; Explore is **Fabric-only** - it has no Forge build on any game version - so no module of
/// this mod may compile or link against a `more_rpg_loot` class: a `common` class that did would
/// blow up with `NoClassDefFoundError` the moment the Forge runtime touched it.
///
/// The whole compile-time surface upstream used was `more_rpg_loot.item.Group.RPG_LOOT_KEY`, which is
/// itself nothing but `RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier("loot_n_explore", "loot.generic"))`.
/// Spelling it out here removes the classpath dependency entirely, which is strictly stronger than
/// hiding it in an `isModLoaded` holder class.
///
/// Everything that actually *registers* into that group still runs behind
/// `Platform.util().isModLoaded(LootNExplore.MOD_ID)` in `LNE_Paladins_Mod.registerItems()`.
public class LootNExplore {
    public static final String MOD_ID = "loot_n_explore";

    /// `more_rpg_loot.item.Group.RPG_LOOT_KEY`
    public static final RegistryKey<ItemGroup> GROUP_KEY =
            RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(MOD_ID, "loot.generic"));
}
