package com.lne_paladins.mixin;

import com.lne_paladins.LNE_Paladins_Mod;
import com.lne_paladins.item.weapons.SirensStaff;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.SpellRegistry;
import net.spell_engine.internals.casting.SpellCast;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.List;

import static net.spell_engine.internals.SpellRegistry.getSpell;

@Mixin(SpellHelper.class)
public class SpellHelperMixin {

    @Inject(at = @At("HEAD"), method = "performSpell", cancellable = true)
    private static void sirensStaff_performSpell_Tail(World world, PlayerEntity player, Identifier spellId, List<Entity> targets, SpellCast.Action action, float progress, CallbackInfo ci) {
        if (!player.isSpectator()) {
            ItemStack stack = player.getEquippedStack(EquipmentSlot.MAINHAND);
            Item item = stack.getItem();

            Spell spell = getSpell(spellId);
            SpellSchool school = getSpell(spellId).school;


            float healing_power = (float)(player.getAttributeValue(SpellSchools.HEALING.attribute));
            Spell.Impact.Action.Type type = spell.impact[0].action.type;

            var target = targets.stream().findFirst();

            if(action == SpellCast.Action.RELEASE &&item instanceof SirensStaff && type.equals(Spell.Impact.Action.Type.HEAL)){
                LNE_Paladins_Mod.LOGGER.info("TRUE");
                if(target.isPresent()){
                    Entity entity = target.get();


                    if(entity instanceof LivingEntity livingEntity){
                        livingEntity.setAbsorptionAmount(10);
                    }
                }

            }
        }
    }

}
