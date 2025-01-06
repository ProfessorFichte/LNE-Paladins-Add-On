package com.lne_paladins.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.spell_engine.api.effect.ActionImpairing;
import net.spell_engine.api.effect.EntityActionsAllowed;
import net.spell_engine.api.effect.Synchronized;

import static com.lne_paladins.LNE_Paladins_Mod.MOD_ID;

public class Effects {
    public static StatusEffect SIRENS_SONG = new CustomStatusEffect(StatusEffectCategory.HARMFUL, 0x01d9cf);

    public static void register() {
        Synchronized.configure(SIRENS_SONG, true);
        ActionImpairing.configure(SIRENS_SONG, EntityActionsAllowed.STUN);

        int ID = 20200;
        Registry.register(Registries.STATUS_EFFECT, ID++, new Identifier(MOD_ID, "sirens_song").toString(), SIRENS_SONG);
    }
}
