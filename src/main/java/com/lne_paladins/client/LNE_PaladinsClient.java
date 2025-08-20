package com.lne_paladins.client;

import com.lne_paladins.client.effect.HolyWeaponRenderer;
import com.lne_paladins.effect.Effects;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.spell_engine.api.effect.CustomModelStatusEffect;
import net.spell_engine.api.render.CustomModels;

import java.util.List;

@Environment(EnvType.CLIENT)
public class LNE_PaladinsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        CustomModels.registerModelIds(List.of(
                HolyWeaponRenderer.modelId
        ));
        CustomModelStatusEffect.register(Effects.HOLY_WEAPON.effect, new HolyWeaponRenderer());
    }
}
