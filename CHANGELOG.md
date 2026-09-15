# 1.2.1+1.20.1

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

# 1.2.1 - 1.21.1
- Drop Forgified Fabric API (FFAPI) as a required dependency

# 1.2.0 - 1.21.1
- Adopt Spell Engine 1.10 - Thanks Daedelus for the PR!

# 1.1.2 - 1.21.1
- Fixed Neoforge Crash
- clean up code
- Replace Infested Stone Bricks with normal Stone Bricks in the Holy Church GH Issue #3

# 1.1.1 - 1.21.1
- Adapt to Spell Engine 1.9.10+ API Changes
**Balancing & Internal Changes:**
- Removed Holy Weapon as an extra spell for Paladin.
- New Spell for Paladin: "Templar's Sky Splitter" 
- Call down a ring of falling templar swords around you, dealing damage & Increasing the attack damage of the paladin
- The model is a placeholder, a new model will be created for this spell.

# 1.1.0 - 1.21.1
**Update to use Spell Engine 1.9.0**
- DISCLAIMER: All spell books and spell scrolls will be reset, due to major API changes.
- Prevention & Holy Weapon are now Tier 5 Spells
- The Spells can now also be learned in the Spell Binding Table
- The additional Spells also got slightly buffed
- Small tweaks in the Loot Tables

# 1.0.9 - 1.21.1
- Fixed the Loot Table Errors in the Logs from the Paladin Recruitment Structure
- Split the Priest and Paladin Loot Tables for Better configuration of loot tables
- Added compatibility for the Oathsworn Paladin's Equipment in the Paladin's Loot Tables!
- Heavily improved the generation of Path's from this Mod's Structure's
- Added a new Illager Occupied variant of the Small Church

# 1.0.8 - 1.21.1
- fix desert_villager jigsaw
- improve some small details in the buildings
- Update loot tables and completely overhaul them!
- The Project now almost completely runs with Datagen!
- move all the structures from loot_n_explore to lne_paladins, so its more clearer that these structures come from this add on

# 1.0.7 - 1.21.1
- Move to Architectury Enviroment for Multiloader
- NeoForge Beta!
- Update Weapon Spell Power
- Add new raided Holy Church variant with Illagers inside
- Add new Desert Mosque structure

# 1.0.6 - 1.21.1
- Spell Engine 1.7.1 Crash Fix
- Holy Weapon & Prevention are now a T4 spell
- they can now also be looted outside the classes structure

# 1.0.5 - 1.21.1
- Spell Engine 1.7

# 1.0.4 - 1.21.1
- Update Mod Icon

# 1.0.3 - 1.21.1
- forgot some shield recipes

# 1.0.2 - 1.21.1
- 4x new LNE-Paladin Shield variants
- new Models for the LNE - Paladin Great Hammer's & Mace's
- a new paladin & priest holy church structure!
- increased separation & spacing for paladin structure sets, because they generated to frequently
- Update Mod License to ARR
- generate small random paths and decoration near some small structures

# 1.0.1 - 1.21.1
- forgot to add sirens tears spell to the elder guardian holy staff

# 1.0.0 - 1.21.1
## Official 1.21.1 Release!
### CHANGES
- Passive Spells For the Weapons are now handled with the new Spell Engine Passive API
- The Class related structures will now contain spell scrolls in their loot chests
- Made some small loot table tweaks
- Siren's Holy Staff's Item Id was changed to -> elder_guardian_holy_staff
- THe Holy Weapon Hits deal increased damage against undead foes