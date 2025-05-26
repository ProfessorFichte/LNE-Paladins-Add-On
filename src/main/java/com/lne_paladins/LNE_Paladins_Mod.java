package com.lne_paladins;

import com.lne_paladins.client.particle.LNEP_Particles;
import com.lne_paladins.config.Default;
import com.lne_paladins.effect.Effects;
import com.lne_paladins.item.LNEShields;
import com.lne_paladins.item.WeaponRegister;
import net.fabricmc.api.ModInitializer;
import com.lne_paladins.config.TweaksConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.spell_engine.api.config.ConfigFile;
import net.tinyconfig.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LNE_Paladins_Mod implements ModInitializer {
	public static final String MOD_ID = "lne_paladins";
	public static final Logger LOGGER = LoggerFactory.getLogger("lne_paladins");
	public static ConfigManager<ConfigFile.Equipment> itemConfig = new ConfigManager<>
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
	public static ConfigManager<ConfigFile.Shields> shieldConfig = new ConfigManager<>
			("shields", new ConfigFile.Shields())
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();

	@Override
	public void onInitialize() {
		tweaksConfig.refresh();
		LNEP_Particles.register();
		Effects.register();
		if(FabricLoader.getInstance().isModLoaded("loot_n_explore")) {
			itemConfig.refresh();
			shieldConfig.refresh();
			LNEShields.register(shieldConfig.value.shields);
			WeaponRegister.register(itemConfig.value.weapons);
			itemConfig.save();
			shieldConfig.save();
		}
	}
}