package com.oldmartijntje.tutorialmod.particle;

import com.oldmartijntje.tutorialmod.AvansEetBanaantjes;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {
    public static final SimpleParticleType PINK_GARNET_PARTICLE =
            registerParticle("pink_garnet_particle", FabricParticleTypes.simple());

    private static SimpleParticleType registerParticle(String name, SimpleParticleType particleType) {
        return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(AvansEetBanaantjes.MOD_ID, name), particleType);
    }

    public static void registerParticles() {
        AvansEetBanaantjes.LOGGER.info("Registering Particles for " + AvansEetBanaantjes.MOD_ID);
    }
}
