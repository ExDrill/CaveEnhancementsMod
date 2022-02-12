package com.exdrill.ce.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class Shockwave extends Particle {
    protected Sprite sprite;
    protected float scale;

    Shockwave(ClientWorld world, double x, double y, double z, double d) {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);
        this.scale = 1;
        this.velocityX = 0;
        this.velocityY = 0;
        this.velocityZ = 0;
        this.red = Math.max(0.0F, MathHelper.sin(((float)d + 0.0F) * 6.2831855F) * 0.65F + 0.35F);
        this.green = Math.max(0.0F, MathHelper.sin(((float)d + 0.33333334F) * 6.2831855F) * 0.65F + 0.35F);
        this.blue = Math.max(0.0F, MathHelper.sin(((float)d + 0.6666667F) * 6.2831855F) * 0.65F + 0.35F);
        this.scale *= 1.5F;
        this.maxAge = 6;
    }

    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        Vec3d vec3d = camera.getPos();
        float f = (float)(MathHelper.lerp((double)tickDelta, this.prevPosX, this.x) - vec3d.getX());
        float g = (float)(MathHelper.lerp((double)tickDelta, this.prevPosY, this.y) - vec3d.getY());
        float h = (float)(MathHelper.lerp((double)tickDelta, this.prevPosZ, this.z) - vec3d.getZ());
        Quaternion quaternion;
        if (this.angle == 0.0F) {
            quaternion = camera.getRotation();
        } else {
            quaternion = new Quaternion(camera.getRotation());
            float i = MathHelper.lerp(tickDelta, this.prevAngle, this.angle);
            quaternion.hamiltonProduct(Vec3f.POSITIVE_Z.getRadialQuaternion(i));
        }

        Vec3f i = new Vec3f(-1.0F, -1.0F, 0.0F);
        i.rotate(quaternion);
        Vec3f[] vec3fs = new Vec3f[]{new Vec3f(-1.0F, -1.0F, 0.0F), new Vec3f(-1.0F, 1.0F, 0.0F), new Vec3f(1.0F, 1.0F, 0.0F), new Vec3f(1.0F, -1.0F, 0.0F)};
        float j = this.getSize(tickDelta);

        for(int k = 0; k < 4; ++k) {
            Vec3f vec3f = vec3fs[k];
            vec3f.rotate(quaternion);
            vec3f.scale(j);
            vec3f.add(f, g, h);
        }

        float k = this.getMinU();
        float vec3f = this.getMaxU();
        float l = this.getMinV();
        float m = this.getMaxV();
        int n = this.getBrightness(tickDelta);
        vertexConsumer.vertex((double)vec3fs[0].getX(), (double)vec3fs[0].getY(), (double)vec3fs[0].getZ()).texture(vec3f, m).color(this.red, this.green, this.blue, this.alpha).light(n).next();
        vertexConsumer.vertex((double)vec3fs[1].getX(), (double)vec3fs[1].getY(), (double)vec3fs[1].getZ()).texture(vec3f, l).color(this.red, this.green, this.blue, this.alpha).light(n).next();
        vertexConsumer.vertex((double)vec3fs[2].getX(), (double)vec3fs[2].getY(), (double)vec3fs[2].getZ()).texture(k, l).color(this.red, this.green, this.blue, this.alpha).light(n).next();
        vertexConsumer.vertex((double)vec3fs[3].getX(), (double)vec3fs[3].getY(), (double)vec3fs[3].getZ()).texture(k, m).color(this.red, this.green, this.blue, this.alpha).light(n).next();
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

    protected void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    protected float getMinU() {
        System.out.println("Get Min U");
        System.out.println(this.sprite.getMinU());
        return this.sprite.getMinU();
    }

    protected float getMaxU() {
        System.out.println("Get Max U");
        System.out.println(this.sprite.getMaxU());
        return this.sprite.getMaxU();
    }

    protected float getMinV() {
        System.out.println("Get Min V");
        System.out.println(this.sprite.getMinV());
        return this.sprite.getMinV();
    }

    protected float getMaxV() {
        System.out.println("Get Max V");
        System.out.println(this.sprite.getMaxV());
        return this.sprite.getMaxV();
    }

    public void setSprite(SpriteProvider spriteProvider) {
        this.setSprite(spriteProvider.getSprite(this.random));
    }
}
