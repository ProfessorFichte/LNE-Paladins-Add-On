package com.lne_paladins.effect;

import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.spell_engine.api.config.ConfigFile;
import net.spell_engine.api.config.EffectConfig;
import net.spell_engine.api.effect.*;

import java.util.ArrayList;
import java.util.List;

import static com.lne_paladins.LNE_Paladins_Mod.MOD_ID;

public class LNE_PaladinsEffects {
    public static final List<Effects.Entry> entries = new ArrayList<>();
    private static Effects.Entry add(Effects.Entry entry) {
        entries.add(entry);
        return entry;
    }

    public static Effects.Entry SIRENS_SONG = add(new Effects.Entry(Identifier.of(MOD_ID, "sirens_song"),
            "Siren's Song",
            "Stun's the target.",
            new CustomStatusEffect(StatusEffectCategory.HARMFUL, 0x01d9cf),
            new EffectConfig(
                    List.of(
                    )
            )
    ));
    public static Effects.Entry HOLY_WEAPON = add(new Effects.Entry(Identifier.of(MOD_ID, "holy_weapon"),
            "Holy Weapon",
            "Enhances your melee hits with heals and additional healing power damage, 100% crit chance against undead.",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0xffffcc),
            new EffectConfig(
                    List.of(
                    )
            )
    ));
    public static Effects.Entry PREVENTION = add(new Effects.Entry(Identifier.of(MOD_ID, "holy_prevention"),
            "Prevention",
            "Heals you when you fall below 20% max health, the heal amount enhances with effect amplifier. If you´re already under 20% when the effect is applied, the healing is reduced.",
            new PreventionStatusEffect(StatusEffectCategory.BENEFICIAL, 0xffffcc),
            new EffectConfig(
                    List.of(
                    )
            )
    ));

    public static void register(ConfigFile.Effects config) {
        ActionImpairing.configure(SIRENS_SONG.effect, EntityActionsAllowed.STUN);
        for (var entry: entries) {
            Synchronized.configure(entry.effect, true);
        }

        Effects.register(entries, config.effects);
    }
}
