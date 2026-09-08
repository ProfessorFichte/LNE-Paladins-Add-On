package com.lne_paladins.forge.client;

import com.lne_paladins.client.entity.TemplarsSkySplitterProjectileRenderer;
import com.lne_paladins.entity.TemplarsSkySplitterProjectile;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.spell_engine.client.gui.ConfigMenuScreen;

/// Client-side Forge wiring. Only ever touched behind `Dist.CLIENT` (see `ForgeMod`), so there is no
/// `@Mod.EventBusSubscriber` — listeners are registered explicitly from {@link #register(IEventBus)}.
public class ForgeClientMod {
    public static void register(IEventBus modBus) {
        modBus.addListener(EventPriority.NORMAL, false, FMLClientSetupEvent.class, ForgeClientMod::onClientSetup);
        modBus.addListener(EventPriority.NORMAL, false, EntityRenderersEvent.RegisterRenderers.class,
                ForgeClientMod::onRegisterRenderers);
    }

    @SuppressWarnings("removal")
    private static void onClientSetup(FMLClientSetupEvent event) {
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, parent) -> new ConfigMenuScreen(parent)));
    }

    private static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(TemplarsSkySplitterProjectile.ENTITY_TYPE,
                TemplarsSkySplitterProjectileRenderer::new);
    }
}
