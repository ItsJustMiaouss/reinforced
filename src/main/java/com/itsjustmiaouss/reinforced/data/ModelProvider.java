package com.itsjustmiaouss.reinforced.data;

import com.itsjustmiaouss.reinforced.block.AbstractReinforcedBlock;
import com.itsjustmiaouss.reinforced.block.ReinforcedBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.TexturedModel;

class ModelProvider extends FabricModelProvider {

    protected ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        ReinforcedBlocks.getReinforcedBlocks().stream()
                .filter(filteredBlock -> filteredBlock instanceof AbstractReinforcedBlock)
                .map(filteredBlock -> (AbstractReinforcedBlock) filteredBlock)
                .forEach(abstractReinforcedBlock -> blockStateModelGenerator.registerSingleton(abstractReinforcedBlock, TexturedModel.CUBE_BOTTOM_TOP));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }
}
