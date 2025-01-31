package com.lne_paladins.api;

import com.lne_paladins.effect.Effects;
import com.lne_paladins.item.weapons.SirensStaff;
import more_rpg_loot.util.HelperMethods;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.Spell;
import net.spell_power.api.SpellPowerTags;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;

import java.util.List;
import java.util.Random;

import static com.lne_paladins.LNE_Paladins_Mod.tweaksConfig;
import static net.spell_engine.internals.SpellRegistry.getSpell;

public class LnePaladinPassives {

    public static void sirenStaffHealPassive(LivingEntity healer, List<Entity> targets, Identifier spellId){
        if (!healer.isSpectator()) {
            ItemStack stack = healer.getEquippedStack(EquipmentSlot.MAINHAND);
            Item item = stack.getItem();

            Spell spell = getSpell(spellId);
            SpellSchool school = getSpell(spellId).school;
            Spell.Impact.Action.Type type = spell.impact[0].action.type;
            var target = targets.stream().findFirst();

            if (item instanceof SirensStaff && type.equals(Spell.Impact.Action.Type.HEAL) && school == SpellSchools.HEALING) {
                //float spell_power_coefficient = spell.impact[0].action.heal.spell_power_coefficient;
                //float healing_power = (float) (healer.getAttributeValue(SpellSchools.HEALING.attribute));
                if (target.isPresent()) {
                    Entity entity = target.get();
                    if (entity instanceof LivingEntity livingEntity) {
                        float random = new Random().nextFloat(1.0F);
                        if (random < tweaksConfig.value.sirens_staff_tears_debuff_chance) {
                            HelperMethods.clearNegativeEffects(livingEntity, true);
                        }
                    }
                }
            }
        }
    }
    public static void sirenStaffAttackPassive(LivingEntity target, Entity attacker, DamageSource source){
        int duration = tweaksConfig.value.sirens_staff_song_duration * 20;
        float random = new Random().nextFloat(1.0F);
        if (attacker instanceof PlayerEntity player && source.isIn(SpellPowerTags.DamageType.ALL) && !target.isSpectator()) {
            ItemStack stack = player.getEquippedStack(EquipmentSlot.MAINHAND);
            Item item = stack.getItem();
            if (item instanceof SirensStaff) {
                if (random < tweaksConfig.value.sirens_staff_song_chance) {
                    target.playSound(SoundEvents.ENTITY_GHAST_AMBIENT, 1, 3);
                    HelperMethods.applyStatusEffect(target, 0, duration, Effects.SIRENS_SONG,
                            0, false, true, false, 0);
                }
            }
        }
    }
}
