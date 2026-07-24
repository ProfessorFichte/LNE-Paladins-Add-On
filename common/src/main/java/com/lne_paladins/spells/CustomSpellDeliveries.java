package com.lne_paladins.spells;

import com.lne_paladins.entity.TemplarsSkySplitterProjectile;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.spell_engine.api.spell.event.SpellHandlers;

import static com.lne_paladins.LNE_Paladins_Mod.MOD_ID;

public class CustomSpellDeliveries {
    private static final int PROJECTILE_COUNT = 5;
    private static final double RADIUS = 4.5;
    private static final double LAUNCH_HEIGHT = 14.0;
    private static final float PROJECTILE_SCALE = 1.5F;

    public static void register() {
        SpellHandlers.registerCustomDelivery(
            Identifier.of(MOD_ID, "sky_splitter"),
            (world, spellEntry, caster, targets, context, targetLocation) -> {
                if (world.isClient) return false;

                var center = caster.getPos();
                for (int i = 0; i < PROJECTILE_COUNT; i++) {
                    var angle = Math.toRadians(360.0 / PROJECTILE_COUNT * i);
                    var offset = new Vec3d(RADIUS, 0, 0).rotateY((float) angle);
                    var spawnPos = center.add(offset).add(0, LAUNCH_HEIGHT, 0);

                    var projectile = new TemplarsSkySplitterProjectile(world, caster, spellEntry, context, PROJECTILE_SCALE);
                    projectile.setPosition(spawnPos);
                    projectile.setVelocity(0, -TemplarsSkySplitterProjectile.INITIAL_FALL_SPEED, 0);
                    world.spawnEntity(projectile);
                }
                return true;
            }
        );
    }
}
