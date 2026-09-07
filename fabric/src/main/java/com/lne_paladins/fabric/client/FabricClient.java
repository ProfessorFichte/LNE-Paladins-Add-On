package com.lne_paladins.fabric.client;

import com.lne_paladins.client.entity.TemplarsSkySplitterProjectileRenderer;
import com.lne_paladins.entity.TemplarsSkySplitterProjectile;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public final class FabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(TemplarsSkySplitterProjectile.ENTITY_TYPE, TemplarsSkySplitterProjectileRenderer::new);
    }
}
