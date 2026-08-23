package com.lne_paladins.client;

import com.lne_paladins.client.entity.TemplarsSkySplitterProjectileRenderer;
import com.lne_paladins.entity.TemplarsSkySplitterProjectile;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class LNE_PaladinsClient{

    public static void init() {
        EntityRendererRegistry.register(TemplarsSkySplitterProjectile.ENTITY_TYPE, TemplarsSkySplitterProjectileRenderer::new);
    }
}
