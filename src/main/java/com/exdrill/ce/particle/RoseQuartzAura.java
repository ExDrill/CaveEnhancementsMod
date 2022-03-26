package com.exdrill.ce.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class RoseQuartzAura extends AnimatedParticle {
    double velX = -1;
    double velY = -1;
    double velZ = -1;

    RoseQuartzAura(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, spriteProvider, 0.0F);
        velX = velocityX;
        velY = velocityY;
        velZ = velocityZ;
        this.velocityX = velX;
        this.velocityY = velY;
        this.velocityZ = velZ;
        this.scale *= 1.5F;
        this.collidesWithWorld = false;
        this.gravityStrength = 0.0F;
        this.maxAge = 25 * 10;
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public void tick() {
        super.tick();

        velocityX = velX;
        velocityY = velY;
        velocityZ = velZ;

        float magnitude = 1F;
        float smoothness = 300;

        velX += random.nextFloat(-magnitude, magnitude) / smoothness;
        velY += random.nextFloat(-magnitude, magnitude) / smoothness;
        velZ += random.nextFloat(-magnitude, magnitude) / smoothness;
    }

    @Environment(EnvType.CLIENT)
    public static class RoseQuartzFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public RoseQuartzFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            RoseQuartzAura glowParticle = new RoseQuartzAura(clientWorld, d, e, f, g, h, i, this.spriteProvider);

            return glowParticle;
        }
    }
}
