package com.lne_paladins.client.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.lne_paladins.LNE_Paladins_Mod.MOD_ID;

public class LNEP_Particles {
    public static final DefaultParticleType PREVENTION_SIGN = FabricParticleTypes.simple();

    public static void register(){
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "prevention_sign"), PREVENTION_SIGN);
    }
}
