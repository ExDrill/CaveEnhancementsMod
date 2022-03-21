package com.exdrill.ce.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class RoseQuartzAura extends AnimatedParticle {


    RoseQuartzAura(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, spriteProvider, 0.0F);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.scale *= 0.5F;
        this.collidesWithWorld = false;
        this.gravityStrength = 0.0F;
        this.maxAge = 60 + this.random.nextInt(12);
        this.setSpriteForAge(spriteProvider);

    }

    @Environment(EnvType.CLIENT)
    public static class RoseQuartzFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public RoseQuartzFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            RoseQuartzAura glowParticle = new RoseQuartzAura(clientWorld, d, e, f, 0.0D, 0.0D, 0.0D, this.spriteProvider);
            glowParticle.setVelocity(g * 0.25D / 2D, h * 3D, i * 0.25D / 2D);
            boolean j = true;
            boolean k = true;
            return glowParticle;
        }


    }
}
