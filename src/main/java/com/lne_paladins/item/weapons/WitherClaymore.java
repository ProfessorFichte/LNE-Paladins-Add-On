package com.lne_paladins.item.weapons;

import more_rpg_loot.item.weapons.WitherMeleeWeapon;
import net.minecraft.item.ToolMaterial;

public class WitherClaymore extends WitherMeleeWeapon {
    public WitherClaymore(ToolMaterial material, Settings settings) {
        this(material, 1, 2.4F, settings);
    }

    public WitherClaymore(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }
}
