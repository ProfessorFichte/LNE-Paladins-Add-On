package com.lne_paladins.mixin;

import com.lne_paladins.LNE_Paladins_Mod;
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
import net.spell_power.api.SpellPowerTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

import static com.lne_paladins.LNE_Paladins_Mod.tweaksConfig;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {


    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V"))
    private void damage_sirensStaff(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        Entity attacker = source.getAttacker();
        LivingEntity entity = (LivingEntity)(Object)this;
        int duration = tweaksConfig.value.sirens_staff_song_duration * 20;
        float random = new Random().nextFloat(1.0F);

        if(attacker instanceof PlayerEntity player && source.isIn(SpellPowerTags.DamageType.ALL) && !entity.isSpectator()){
            ItemStack stack = player.getEquippedStack(EquipmentSlot.MAINHAND);
            Item item = stack.getItem();
            if(item instanceof SirensStaff){
                if (random < tweaksConfig.value.sirens_staff_song_chance) {
                    entity.playSound(SoundEvents.ENTITY_GHAST_AMBIENT,1,3);
                    HelperMethods.applyStatusEffect(entity,0,duration, Effects.SIRENS_SONG,
                            0,false,true,false,0);
                }
            }
        }
        return;
    }
}
