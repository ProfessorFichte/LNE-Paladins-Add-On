package com.lne_paladins.client;

import com.lne_paladins.client.effect.HolyWeaponRenderer;
import com.lne_paladins.effect.LNE_PaladinsEffects;
import net.spell_engine.api.effect.CustomModelStatusEffect;

public class LNE_PaladinsClient{

    public static void init() {
        CustomModelStatusEffect.register(LNE_PaladinsEffects.HOLY_WEAPON.effect, new HolyWeaponRenderer());
    }
}
