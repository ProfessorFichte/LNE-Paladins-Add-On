package com.lne_paladins;

import com.lne_paladins.client.particle.LNEP_Particles;
import com.lne_paladins.config.Default;
import com.lne_paladins.effect.Effects;
import com.lne_paladins.item.WeaponRegister;
import net.fabricmc.api.ModInitializer;
import com.lne_paladins.config.TweaksConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.spell_engine.api.item.ItemConfig;
import net.tinyconfig.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LNE_Paladins_Mod implements ModInitializer {
	public static final String MOD_ID = "lne_paladins";
	public static final Logger LOGGER = LoggerFactory.getLogger("lne_paladins");
	public static ConfigManager<ItemConfig> itemConfig = new ConfigManager<>
			("items_v0", Default.itemConfig)
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();
	public static ConfigManager<TweaksConfig> tweaksConfig = new ConfigManager<TweaksConfig>
			("tweaks", new TweaksConfig())
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();

	@Override
	public void onInitialize() {
		tweaksConfig.refresh();
		LNEP_Particles.register();
		if(FabricLoader.getInstance().isModLoaded("loot_n_explore")) {
			Effects.register();
			itemConfig.refresh();
			WeaponRegister.register(itemConfig.value.weapons);
			itemConfig.save();
		}
	}
}