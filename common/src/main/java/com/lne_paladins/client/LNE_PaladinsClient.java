package com.lne_paladins.client;

import com.lne_paladins.client.entity.TemplarsSkySplitterProjectileRenderer;
import com.lne_paladins.entity.TemplarsSkySplitterProjectile;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class LNE_PaladinsClient{

    public static void init() {
        // The `SpellTooltip.addDescriptionMutator` loop is gone: the mod's only mutator was a
        // config-derived effect-modifier read, now expressed declaratively by a `TooltipTokens.effect`
        // token baked into the spell description, so there is nothing left to register here.
        EntityRendererRegistry.register(TemplarsSkySplitterProjectile.ENTITY_TYPE, TemplarsSkySplitterProjectileRenderer::new);
    }
}
