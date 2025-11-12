package com.lne_paladins.fabric.client;

import com.lne_paladins.client.LNE_PaladinsClient;
import net.fabricmc.api.ClientModInitializer;

public final class FabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        LNE_PaladinsClient.init();
    }
}
