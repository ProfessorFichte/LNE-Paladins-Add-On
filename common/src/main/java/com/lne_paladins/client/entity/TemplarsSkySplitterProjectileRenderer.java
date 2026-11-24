package com.lne_paladins.client.entity;

import com.lne_paladins.entity.TemplarsSkySplitterProjectile;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.spell_engine.api.render.CustomLayers;
import net.spell_engine.api.render.CustomModels;
import net.spell_engine.api.render.LightEmission;

public class TemplarsSkySplitterProjectileRenderer<T extends TemplarsSkySplitterProjectile> extends EntityRenderer<T> {
    private final ItemRenderer itemRenderer;

    public static final Identifier MODEL_ID = Identifier.of("lne_paladins", "spell_projectile/sky_splitter");
    private static final RenderLayer RENDER_LAYER = CustomLayers.spellObject(LightEmission.NONE);
    private static final float MODEL_LIFT = 1.27F;

    public TemplarsSkySplitterProjectileRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public Identifier getTexture(T entity) {
        return null;
    }

    @Override
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrixStack, vertexConsumers, light);

        matrixStack.push();
        var scale = entity.getRenderScale();
        matrixStack.scale(scale, scale, scale);
        matrixStack.translate(0, MODEL_LIFT, 0);
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));

        CustomModels.render(
            RENDER_LAYER,
            itemRenderer,
            MODEL_ID,
            matrixStack,
            vertexConsumers,
            light,
            entity.getId()
        );

        matrixStack.pop();
    }
}
