package com.lne_paladins.client;

import com.lne_paladins.client.effect.HolyWeaponRenderer;
import com.lne_paladins.effect.LNE_PaladinsEffects;
import net.spell_engine.api.effect.CustomModelStatusEffect;
import net.spell_engine.api.render.CustomModels;

import java.util.List;

public class LNE_PaladinsClient{

    public static void init() {
        CustomModels.registerModelIds(List.of(
                HolyWeaponRenderer.modelId
        ));
        CustomModelStatusEffect.register(LNE_PaladinsEffects.HOLY_WEAPON.effect, new HolyWeaponRenderer());
    }
}
