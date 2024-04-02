package com.itsjustmiaouss.reinforced.data;


import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ReinforcedDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(TagGenerator::new);
        pack.addProvider(ModelProvider::new);
        pack.addProvider(LootTableProvider::new);
    }
}
