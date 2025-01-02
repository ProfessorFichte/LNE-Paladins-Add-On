package com.lne_paladins.item.weapons;

import more_rpg_loot.item.weapons.DragonMeeleeWeapon;
import net.minecraft.item.ToolMaterial;

public class DragonClaymore extends DragonMeeleeWeapon {
    public DragonClaymore(ToolMaterial material, Settings settings) {
        this(material, 1, 2.4F, settings);
    }

    public DragonClaymore(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }
}
