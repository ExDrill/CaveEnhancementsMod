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
        this.red = 1;
        this.green = 1;
        this.blue = 1;
        this.maxAge = 6;
        //this.spacingXZ = 10;
        //this.spacingY = 10;
    }

    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        Vec3d cameraPos = camera.getPos();

        float x = (float)(MathHelper.lerp((double)tickDelta, this.prevPosX, this.x) - cameraPos.getX());
        float y = (float)(MathHelper.lerp((double)tickDelta, this.prevPosY, this.y) - cameraPos.getY());
        float z = (float)(MathHelper.lerp((double)tickDelta, this.prevPosZ, this.z) - cameraPos.getZ());

        float size = this.getSize(tickDelta);

        float minU = this.getMinU();
        float maxU = this.getMaxU();
        float minV = this.getMinV();
        float maxV = this.getMaxV();

        int light = this.getBrightness(tickDelta);

        Vec3f[] vertices = new Vec3f[]{new Vec3f(-1.0F, -1.0F, 0.0F), new Vec3f(-1.0F, 1.0F, 0.0F), new Vec3f(1.0F, 1.0F, 0.0F), new Vec3f(1.0F, -1.0F, 0.0F)};

        for(int i = 0; i < 4; ++i) {
            Vec3f vec3f = vertices[i];
            vec3f.scale(size);
            vec3f.add(x, y, z);
        }

        vertexConsumer.vertex(vertices[0].getX(), vertices[0].getY(), vertices[0].getZ()).texture(maxU, maxV).color(this.red, this.green, this.blue, this.alpha).light(light).next();
        vertexConsumer.vertex(vertices[1].getX(), vertices[1].getY(), vertices[1].getZ()).texture(maxU, minV).color(this.red, this.green, this.blue, this.alpha).light(light).next();
        vertexConsumer.vertex(vertices[2].getX(), vertices[2].getY(), vertices[2].getZ()).texture(minU, minV).color(this.red, this.green, this.blue, this.alpha).light(light).next();
        vertexConsumer.vertex(vertices[3].getX(), vertices[3].getY(), vertices[3].getZ()).texture(minU, maxV).color(this.red, this.green, this.blue, this.alpha).light(light).next();

        vertices = new Vec3f[]{new Vec3f(1.0F, -1.0F, 0.0F), new Vec3f(1.0F, 1.0F, 0.0F), new Vec3f(-1.0F, 1.0F, 0.0F), new Vec3f(-1.0F, -1.0F, 0.0F)};

        for(int i = 0; i < 4; ++i) {
            Vec3f vec3f = vertices[i];
            vec3f.scale(size);
            vec3f.add(x, y, z);
        }

        vertexConsumer.vertex(vertices[0].getX(), vertices[0].getY(), vertices[0].getZ()).texture(maxU, maxV).color(this.red, this.green, this.blue, this.alpha).light(light).next();
        vertexConsumer.vertex(vertices[1].getX(), vertices[1].getY(), vertices[1].getZ()).texture(maxU, minV).color(this.red, this.green, this.blue, this.alpha).light(light).next();
        vertexConsumer.vertex(vertices[2].getX(), vertices[2].getY(), vertices[2].getZ()).texture(minU, minV).color(this.red, this.green, this.blue, this.alpha).light(light).next();
        vertexConsumer.vertex(vertices[3].getX(), vertices[3].getY(), vertices[3].getZ()).texture(minU, maxV).color(this.red, this.green, this.blue, this.alpha).light(light).next();
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
