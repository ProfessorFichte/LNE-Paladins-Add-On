package com.lne_paladins.item.weapons;

import more_rpg_loot.item.weapons.GlacialMeleeWeapon;
import net.minecraft.item.ToolMaterial;

public class GlacialClaymore extends GlacialMeleeWeapon {
    public GlacialClaymore(ToolMaterial material, Settings settings) {
        this(material, 1, 2.4F, settings);
    }

    public GlacialClaymore(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }
}

