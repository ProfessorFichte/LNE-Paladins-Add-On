package com.lne_paladins.datagen;

import com.google.gson.JsonObject;
import com.lne_paladins.item.WeaponRegister;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import static com.lne_paladins.LNE_Paladins_Mod.MOD_ID;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

        for (var entry : WeaponRegister.entries) {
            Item item = entry.item();
            if (item == null) continue;
            Identifier itemId = Registries.ITEM.getId(item);
            String name = itemId.getPath();
            Identifier modelId = Identifier.of(itemId.getNamespace(), "item/" + name);
            if(name.contains("staff")||name.contains("claymore")){
                JsonObject json = new JsonObject();
                if (name.contains("staff")) {
                    json.addProperty("parent", "paladins:item/base/staff_24");
                } else if(name.contains("claymore")){
                    json.addProperty("parent", "paladins:item/base/claymore_28");
                }
                JsonObject textures = new JsonObject();
                textures.addProperty("layer0", MOD_ID + ":item/" + name);
                json.add("textures", textures);

                itemModelGenerator.writer.accept(modelId, () -> json);
            }
        }
    }
}
