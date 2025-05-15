package com.lne_paladins.client.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.lne_paladins.LNE_Paladins_Mod.MOD_ID;

public class LNEP_Particles {
    public static final SimpleParticleType PREVENTION_SIGN = FabricParticleTypes.simple();

    public static void register(){
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "prevention_sign"), PREVENTION_SIGN);
    }
}
