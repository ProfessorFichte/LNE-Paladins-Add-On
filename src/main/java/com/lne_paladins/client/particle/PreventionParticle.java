package com.lne_paladins.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.spell_engine.client.particle.SpellFlameParticle;
import net.spell_engine.client.util.Color;

public class PreventionParticle extends SpellFlameParticle {
    public PreventionParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        super(clientWorld, d, e, f, g, h, i);
    }

    @Environment(EnvType.CLIENT)
    public static class PreventionSignFactory extends PopupSignFactory {
        public PreventionSignFactory(SpriteProvider spriteProvider) {
            super(spriteProvider, Color.HOLY);
        }
    }
}
