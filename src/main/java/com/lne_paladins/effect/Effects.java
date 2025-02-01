package com.lne_paladins.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
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
    public static StatusEffect HOLY_WEAPON = new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0xffffcc);
    public static StatusEffect PREVENTION = new PreventionStatusEffect(StatusEffectCategory.BENEFICIAL, 0xffffcc);

    public static void register() {
        HOLY_WEAPON.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, "8df38693-8f24-4c8c-b346-75ab7e6cc1aa",
                0.1F, EntityAttributeModifier.Operation.MULTIPLY_BASE);

        Synchronized.configure(SIRENS_SONG, true);
        Synchronized.configure(HOLY_WEAPON, true);
        Synchronized.configure(PREVENTION, true);

        ActionImpairing.configure(SIRENS_SONG, EntityActionsAllowed.STUN);

        int ID = 20200;
        Registry.register(Registries.STATUS_EFFECT, ID++, new Identifier(MOD_ID, "sirens_song").toString(), SIRENS_SONG);
        Registry.register(Registries.STATUS_EFFECT, ID++, new Identifier(MOD_ID, "holy_weapon").toString(), HOLY_WEAPON);
        Registry.register(Registries.STATUS_EFFECT, ID++, new Identifier(MOD_ID, "holy_prevention").toString(), PREVENTION);
    }
}
