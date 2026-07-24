package com.lne_paladins.client;

import com.lne_paladins.client.entity.TemplarsSkySplitterProjectileRenderer;
import com.lne_paladins.entity.TemplarsSkySplitterProjectile;
import com.lne_paladins.spells.LNE_PaladinsSpells;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.spell_engine.client.gui.SpellTooltip;

public class LNE_PaladinsClient{

    public static void init() {
        for (var spell: LNE_PaladinsSpells.entries) {
            if (spell.mutator() != null) {
                SpellTooltip.addDescriptionMutator(spell.id(), spell.mutator());
            }
        }
        EntityRendererRegistry.register(TemplarsSkySplitterProjectile.ENTITY_TYPE, TemplarsSkySplitterProjectileRenderer::new);
    }
}
