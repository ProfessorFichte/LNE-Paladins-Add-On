package com.lne_paladins.item.weapons;

import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.spell_engine.api.item.weapon.StaffItem;

import java.util.List;

public class SirensStaff extends StaffItem {
    public SirensStaff(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("lore.loot_n_explore.elder_guardian_weapon").formatted(Formatting.GOLD));
        tooltip.add(Text.translatable("passive.loot_n_explore.sirens_holy_staff_ally").formatted(Formatting.AQUA));
        tooltip.add(Text.translatable("passive.loot_n_explore.sirens_holy_staff_ally_1").formatted(Formatting.AQUA));
        tooltip.add(Text.translatable("passive.loot_n_explore.sirens_holy_staff_enemy").formatted(Formatting.AQUA));
        tooltip.add(Text.translatable("passive.loot_n_explore.sirens_holy_staff_enemy_1").formatted(Formatting.AQUA));

    }
}
