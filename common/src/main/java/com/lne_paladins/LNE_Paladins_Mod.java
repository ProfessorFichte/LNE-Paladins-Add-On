package com.lne_paladins;

import com.lne_paladins.config.Default;
import com.lne_paladins.effect.LNE_PaladinsEffects;
import com.lne_paladins.entity.ModEntitiesRegistry;
import com.lne_paladins.item.LNEShields;
import com.lne_paladins.item.WeaponRegister;
import com.lne_paladins.config.TweaksConfig;
import com.lne_paladins.spells.CustomSpellDeliveries;
import net.spell_engine.Platform;
import net.spell_engine.rpg_series.config.ConfigFile;
import net.tiny_config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LNE_Paladins_Mod {
	public static final String MOD_ID = "lne_paladins";
	public static final Logger LOGGER = LoggerFactory.getLogger("lne_paladins");
	public static ConfigManager<ConfigFile.Equipment> itemConfig = new ConfigManager<>
			("items_v1", Default.itemConfig)
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
	public static ConfigManager<ConfigFile.Effects> effectConfig = new ConfigManager<>
			("effects", new ConfigFile.Effects())
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();

	public static void init() {
		tweaksConfig.refresh();
		CustomSpellDeliveries.register();
	}
	public static void registerEntities(){
		ModEntitiesRegistry.registerEntities();
	}
	public static void registerItems(){
		if(Platform.util().isModLoaded("loot_n_explore")) {
			itemConfig.refresh();
			shieldConfig.refresh();
			effectConfig.refresh();
			LNEShields.register(shieldConfig.value.shields);
			WeaponRegister.register(itemConfig.value.weapons);
			itemConfig.save();
			shieldConfig.save();
		}
	}
	public static void registerEffects(){
		LNE_PaladinsEffects.register(effectConfig.value);
		effectConfig.save();
	}

}