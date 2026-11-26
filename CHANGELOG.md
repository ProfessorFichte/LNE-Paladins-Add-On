# Changelog

All notable changes to LNE Paladins & Priests Add-On are documented in this file.
The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/).

## [1.3.1+1.21.1]

### Changed

- Works with Loot & Explore 1.1.0 (the 1.21.1 rewrite) on Fabric and NeoForge
- Needs More RPG Library 2.8.4 or newer
- Templar's Sky Splitter has its new sword model. The swords now stay stuck in the ground for 5 seconds and pulse a growing holy circle every second, hitting with the spell again each time, before they vanish
- Loot tables are now made by datagen instead of hand-written files

### Removed

- Oathsworn Paladins compatibility removed

### Fixed

- The Ender Dragon shield (now Dragon Bulwark) and the Elder Guardian shield (now Coral Reef Guardian) no longer have each other's names
- The Loot & Explore themed Maces are in the `paladins:maces` tag again, the tag listed item ids that don't exist
- A few chests in the Holy Church and Holy Mosque now roll loot instead of having fixed items and get converted by Lootr (GH Issue #4)
- Evokers, Pillagers, Vindicators, Witches and Villagers placed by the Paladins structures no longer despawn
- Old structure backup files are no longer packed into the mod
- One Pillager in the Holy Mosque spawns again, its spawn point still pointed at the old Loot & Explore location
- The Holy Mosque now also generates in modded desert biomes from the `c:is_desert` biome tag (the old `c:desert` tag no longer exists in 1.21.1)

## [1.3.0+1.21.1]

### Changed

- Adopt Spell Engine 1.10.9 & More RPG Library 2.8.0
- **BREAKING for modpack/datapack developers:** More RPG Library's namespace was renamed `more_rpg_classes` -> `mrpg_lib` (and its advancement root moved to `mrpg_lib:root`). If you have any data files, loot tables, tags or advancements that reference the old `more_rpg_classes:`/`more_rpg_content:` ids from this mod or MRPGL, update them to `mrpg_lib:`
- Updated internal advancement parents, mod dependency declarations (fabric.mod.json, neoforge.mods.toml)

## [1.2.2+1.21.1]

### Fixed

- fixed a crash when opening creative inventory

## [1.2.1+1.21.1]

### Changed

- Drop Forgified Fabric API (FFAPI) as a required dependency

### Fixed

- Fixed a crash when opening the creative inventory without Loot & Explore installed

## [1.2.0+1.21.1]

### Changed

- Adopt Spell Engine 1.10 - Thanks Daedelus for the PR!

## [1.1.2+1.21.1]

### Changed

- clean up code
- Replace Infested Stone Bricks with normal Stone Bricks in the Holy Church GH Issue #3

### Fixed

- Fixed Neoforge Crash

## [1.1.1+1.21.1]

**Balancing & Internal Changes:**

### Added

- New Spell for Paladin: "Templar's Sky Splitter" 

### Changed

- Adapt to Spell Engine 1.9.10+ API Changes
- Call down a ring of falling templar swords around you, dealing damage & Increasing the attack damage of the paladin
- The model is a placeholder, a new model will be created for this spell.

### Removed

- Removed Holy Weapon as an extra spell for Paladin.

## [1.1.0+1.21.1]

**Update to use Spell Engine 1.9.0**

### Changed

- DISCLAIMER: All spell books and spell scrolls will be reset, due to major API changes.
- Prevention & Holy Weapon are now Tier 5 Spells
- The Spells can now also be learned in the Spell Binding Table
- The additional Spells also got slightly buffed
- Small tweaks in the Loot Tables

## [1.0.9+1.21.1]

### Added

- Added compatibility for the Oathsworn Paladin's Equipment in the Paladin's Loot Tables!
- Added a new Illager Occupied variant of the Small Church

### Changed

- Split the Priest and Paladin Loot Tables for Better configuration of loot tables
- Heavily improved the generation of Path's from this Mod's Structure's

### Fixed

- Fixed the Loot Table Errors in the Logs from the Paladin Recruitment Structure

## [1.0.8+1.21.1]

### Changed

- improve some small details in the buildings
- Update loot tables and completely overhaul them!
- The Project now almost completely runs with Datagen!
- move all the structures from loot_n_explore to lne_paladins, so its more clearer that these structures come from this add on

### Fixed

- fix desert_villager jigsaw

## [1.0.7+1.21.1]

### Added

- Add new raided Holy Church variant with Illagers inside
- Add new Desert Mosque structure

### Changed

- Move to Architectury Enviroment for Multiloader
- NeoForge Beta!
- Update Weapon Spell Power

## [1.0.6+1.21.1]

### Changed

- Spell Engine 1.7.1 Crash Fix
- Holy Weapon & Prevention are now a T4 spell
- they can now also be looted outside the classes structure

## [1.0.5+1.21.1]

### Changed

- Spell Engine 1.7

## [1.0.4+1.21.1]

### Changed

- Update Mod Icon

## [1.0.3+1.21.1]

### Changed

- forgot some shield recipes

## [1.0.2+1.21.1]

### Added

- new Models for the LNE - Paladin Great Hammer's & Mace's

### Changed

- 4x new LNE-Paladin Shield variants
- a new paladin & priest holy church structure!
- increased separation & spacing for paladin structure sets, because they generated to frequently
- Update Mod License to ARR
- generate small random paths and decoration near some small structures

## [1.0.1+1.21.1]

### Changed

- forgot to add sirens tears spell to the elder guardian holy staff

## [1.0.0+1.21.1]

### Changed

**Official 1.21.1 Release!**

- Passive Spells For the Weapons are now handled with the new Spell Engine Passive API
- The Class related structures will now contain spell scrolls in their loot chests
- Made some small loot table tweaks
- Siren's Holy Staff's Item Id was changed to -> elder_guardian_holy_staff
- THe Holy Weapon Hits deal increased damage against undead foes
