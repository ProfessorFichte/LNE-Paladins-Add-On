package com.lne_paladins.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.spell_engine.api.effect.ActionImpairing;
import net.spell_engine.api.effect.EntityActionsAllowed;
import net.spell_engine.api.effect.Synchronized;

import java.util.ArrayList;

import static com.lne_paladins.LNE_Paladins_Mod.MOD_ID;

public class Effects {
    private static final ArrayList<Entry> entries = new ArrayList<Entry>();
    public static class Entry {
        public final Identifier id;
        public final StatusEffect effect;
        public RegistryEntry<StatusEffect> registryEntry;

        public Entry(String name, StatusEffect effect) {
            this.id = Identifier.of(MOD_ID, name);
            this.effect = effect;
            entries.add(this);
        }

        public void register() {
            registryEntry = Registry.registerReference(Registries.STATUS_EFFECT, id, effect);
        }

        public Identifier modifierId() {
            return Identifier.of(MOD_ID, "effect." + id.getPath());
        }
    }
    public static final Entry SIRENS_SONG =  new Entry("sirens_song",
            new CustomStatusEffect(StatusEffectCategory.HARMFUL, 0x01d9cf));
    public static final Entry HOLY_WEAPON =  new Entry("holy_weapon",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0xffffcc));
    public static final Entry PREVENTION =  new Entry("holy_prevention",
            new PreventionStatusEffect(StatusEffectCategory.BENEFICIAL, 0xffffcc));

    public static void register() {
        Synchronized.configure(SIRENS_SONG.effect, true);
        Synchronized.configure(HOLY_WEAPON.effect, true);
        Synchronized.configure(PREVENTION.effect, true);

        ActionImpairing.configure(SIRENS_SONG.effect, EntityActionsAllowed.STUN);

        for (Entry entry: entries) {
            entry.register();
        }
    }
}
