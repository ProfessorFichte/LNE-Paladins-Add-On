# 1.2.1+1.20.1

> ### ⚠️ Read this before updating
>
> This release is a **major technical overhaul and is not backwards compatible.**
>
> - **Requires the matching Spell Engine and More RPG Library releases.** This version will not run on
>   Spell Engine **0.9.x**, and mods built against 0.9.x will not work alongside it.
> - **Update the whole set together.** Spell Engine, More RPG Library and every RPG Series mod must be on
>   matching versions. Mixing in an older add-on will break at startup or misbehave in play.
>
> **Back up your world before updating.**

- Thanks to Daedelus for the PR!
- Ported to Minecraft 1.20.1 (Fabric + Forge 47). NeoForge is replaced by Forge on this line; the same
  Forge jar also loads on NeoForge 1.20.1.
- Requires the matching 1.20.1 releases of Spell Engine (1.10.5), Spell Power (1.6.0), More RPG Library
  (2.7.2), Paladins & Priests (3.1.1) and Armor Model API (1.0.0). Loot & Explore (1.0.22) stays optional.
- Every registry write goes through Forge's `RegisterEvent` window, so the mod also boots on Forge 47.0-47.3
  and on NeoForge 1.20.1, which never unlock the vanilla registries.
- All 28 structure templates were re-exported for 1.20.1: the item frames, armour stands, chests, barrels,
  campfires, bookshelves and the 61 banner patterns inside the churches and mosques all display again.

### Accepted 1.20.1 limitations

- The Holy Church and Holy Mosque jigsaw structures have their `size` lowered from 12 to 7 - that is the
  hard maximum 1.20.1 accepts. Both still generate in full; the paladin paths radiating out of them simply
  branch five levels less far.
- The 27 ominous banners placed in the raided structures lose their "hide additional tooltip" flag, which
  has no 1.20.1 block-entity equivalent. The banners and their names are unchanged.
- Loot & Explore has no Forge build on any game version, so on Forge this add-on contributes only its two
  spells, its three status effects and the Templar's Sky Splitter projectile - the thirteen weapons, the
  four shields and their smithing recipes need Loot & Explore and are skipped there, exactly as they are
  on a Fabric install without it.
