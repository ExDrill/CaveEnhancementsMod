package com.exdrill.ce.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class Shockwave extends SpriteBillboardParticle {
    Shockwave(ClientWorld world, double x, double y, double z, double d) {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);
        this.velocityMultiplier = 0.66F;
        this.field_28787 = true;
        this.velocityX *= 0.009999999776482582D;
        this.velocityY *= 0.009999999776482582D;
        this.velocityZ *= 0.009999999776482582D;
        this.velocityY += 0.2D;
        this.red = Math.max(0.0F, MathHelper.sin(((float)d + 0.0F) * 6.2831855F) * 0.65F + 0.35F);
        this.green = Math.max(0.0F, MathHelper.sin(((float)d + 0.33333334F) * 6.2831855F) * 0.65F + 0.35F);
        this.blue = Math.max(0.0F, MathHelper.sin(((float)d + 0.6666667F) * 6.2831855F) * 0.65F + 0.35F);
        this.scale *= 1.5F;
        this.maxAge = 6;
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    public float getSize(float tickDelta) {
        return this.scale * MathHelper.clamp(((float)this.age + tickDelta) / (float)this.maxAge * 32.0F, 0.0F, 1.0F);
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            Shockwave shockwave = new Shockwave(clientWorld, d, e, f, g);
            shockwave.setSprite(this.spriteProvider);
            return shockwave;
        }
    }
}
