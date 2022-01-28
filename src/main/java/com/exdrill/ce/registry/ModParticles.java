package com.exdrill.ce.registry;

import com.exdrill.ce.Main;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModParticles {

    public static final DefaultParticleType SMALL_GOOP_DRIP = FabricParticleTypes.simple();

    public static void registerParticles() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(Main.NAMESPACE, "small_goop_drip"), SMALL_GOOP_DRIP);
    }


}
