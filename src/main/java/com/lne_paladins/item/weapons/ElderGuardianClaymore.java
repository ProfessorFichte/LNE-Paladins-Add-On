package com.lne_paladins.item.weapons;

import more_rpg_loot.item.weapons.ElderGuardianMeleeWeapon;
import net.minecraft.item.ToolMaterial;

public class ElderGuardianClaymore extends ElderGuardianMeleeWeapon {
    public ElderGuardianClaymore(ToolMaterial material, Settings settings) {
        this(material, 1, 2.4F, settings);
    }

    public ElderGuardianClaymore(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }
}
