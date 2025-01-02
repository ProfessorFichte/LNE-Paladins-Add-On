package com.lne_paladins.config;

import net.spell_engine.api.item.ItemConfig;
import com.lne_paladins.item.WeaponRegister;

public class Default {
    public final static ItemConfig itemConfig;
    static {
        itemConfig = new ItemConfig();
        for (var weapon: WeaponRegister.entries) {
            itemConfig.weapons.put(weapon.name(), weapon.defaults());
        }

    }
}
