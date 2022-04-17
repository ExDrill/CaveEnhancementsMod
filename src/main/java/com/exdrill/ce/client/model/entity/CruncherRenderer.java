package com.exdrill.ce.client.model.entity;

import com.exdrill.ce.client.model.entity.model.CruncherModel;
import com.exdrill.ce.entity.CruncherEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.renderers.geo.ExtendedGeoEntityRenderer;

public class CruncherRenderer<T extends MobEntity> extends ExtendedGeoEntityRenderer<CruncherEntity> {
    public CruncherRenderer(EntityRendererFactory.Context context) {
        super(context, new CruncherModel());
    }

    @Override
    public RenderLayer getRenderType(CruncherEntity animatable, float partialTicks, MatrixStack stack,
                                     VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                     Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }

    @Nullable
    @Override
    protected Identifier getTextureForBone(String boneName, CruncherEntity currentEntity) {
        return null;
    }

    @Nullable
    @Override
    protected ItemStack getHeldItemForBone(String boneName, CruncherEntity currentEntity) {
        ItemStack itemStack = currentEntity.getEquippedStack(EquipmentSlot.MAINHAND);
        Item item2 = itemStack.getItem();
        return boneName.equals("Item") ? new ItemStack(item2) : null;
    }

    @Override
    protected ModelTransformation.Mode getCameraTransformForItemAtBone(ItemStack boneItem, String boneName) {
        return ModelTransformation.Mode.NONE;
    }

    @Nullable
    @Override
    protected BlockState getHeldBlockForBone(String boneName, CruncherEntity currentEntity) {
        return null;
    }

    @Override
    protected void preRenderItem(MatrixStack matrixStack, ItemStack item, String boneName, CruncherEntity currentEntity, IBone bone) {


        ItemStack itemStack = currentEntity.getEquippedStack(EquipmentSlot.MAINHAND);
        Item item2 = itemStack.getItem();

        if (!itemStack.isEmpty()) {
            matrixStack.scale(0.55F, 0.55F, 0.55F);
        }  else {
            matrixStack.scale(0.0F, 0.0F, 0.0F);
        }

    }

    @Override
    protected void preRenderBlock(BlockState block, String boneName, CruncherEntity currentEntity) {

    }

    @Override
    protected void postRenderItem(MatrixStack matrixStack, ItemStack item, String boneName, CruncherEntity currentEntity, IBone bone) {
    }

    @Override
    protected void postRenderBlock(BlockState block, String boneName, CruncherEntity currentEntity) {

    }

    public void render(T mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render((CruncherEntity) mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
        Entity entity = mobEntity.getHoldingEntity();
        if (entity != null) {
            this.renderLeash(mobEntity, g, matrixStack, vertexConsumerProvider, entity);
        }
    }



    private <E extends Entity> void renderLeash(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider provider, E holdingEntity) {
        matrices.push();
        Vec3d vec3d = holdingEntity.getLeashPos(tickDelta);
        double d = (double)(MathHelper.lerp(tickDelta, entity.bodyYaw, entity.prevBodyYaw) * 0.017453292F) + 1.5707963267948966D;
        Vec3d vec3d2 = entity.getLeashOffset();
        double e = Math.cos(d) * vec3d2.z + Math.sin(d) * vec3d2.x;
        double f = Math.sin(d) * vec3d2.z - Math.cos(d) * vec3d2.x;
        double g = MathHelper.lerp((double)tickDelta, entity.prevX, entity.getX()) + e;
        double h = MathHelper.lerp((double)tickDelta, entity.prevY, entity.getY()) + vec3d2.y;
        double i = MathHelper.lerp((double)tickDelta, entity.prevZ, entity.getZ()) + f;
        matrices.translate(e, vec3d2.y, f);
        float j = (float)(vec3d.x - g);
        float k = (float)(vec3d.y - h);
        float l = (float)(vec3d.z - i);
        float m = 0.025F;
        VertexConsumer vertexConsumer = provider.getBuffer(RenderLayer.getLeash());
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        float n = MathHelper.fastInverseSqrt(j * j + l * l) * 0.025F / 2.0F;
        float o = l * n;
        float p = j * n;
        BlockPos blockPos = new BlockPos(entity.getCameraPosVec(tickDelta));
        BlockPos blockPos2 = new BlockPos(holdingEntity.getCameraPosVec(tickDelta));
        int q = this.getBlockLight((CruncherEntity) entity, blockPos);
        int r = this.dispatcher.getRenderer(holdingEntity).getLight(holdingEntity, tickDelta);
        int s = entity.world.getLightLevel(LightType.SKY, blockPos);
        int t = entity.world.getLightLevel(LightType.SKY, blockPos2);

        int u;
        for(u = 0; u <= 24; ++u) {
            renderLeashPiece(vertexConsumer, matrix4f, j, k, l, q, r, s, t, 0.025F, 0.025F, o, p, u, false);
        }

        for(u = 24; u >= 0; --u) {
            renderLeashPiece(vertexConsumer, matrix4f, j, k, l, q, r, s, t, 0.025F, 0.0F, o, p, u, true);
        }

        matrices.pop();
    }

    private static void renderLeashPiece(VertexConsumer vertexConsumer, Matrix4f positionMatrix, float f, float g, float h, int leashedEntityBlockLight, int holdingEntityBlockLight, int leashedEntitySkyLight, int holdingEntitySkyLight, float i, float j, float k, float l, int pieceIndex, boolean isLeashKnot) {
        float m = (float)pieceIndex / 24.0F;
        int n = (int)MathHelper.lerp(m, (float)leashedEntityBlockLight, (float)holdingEntityBlockLight);
        int o = (int)MathHelper.lerp(m, (float)leashedEntitySkyLight, (float)holdingEntitySkyLight);
        int p = LightmapTextureManager.pack(n, o);
        float q = pieceIndex % 2 == (isLeashKnot ? 1 : 0) ? 0.7F : 1.0F;
        float r = 0.5F * q;
        float s = 0.4F * q;
        float t = 0.3F * q;
        float u = f * m;
        float v = g > 0.0F ? g * m * m : g - g * (1.0F - m) * (1.0F - m);
        float w = h * m;
        vertexConsumer.vertex(positionMatrix, u - k, v + j, w + l).color(r, s, t, 1.0F).light(p).next();
        vertexConsumer.vertex(positionMatrix, u + k, v + i - j, w - l).color(r, s, t, 1.0F).light(p).next();
    }


}
