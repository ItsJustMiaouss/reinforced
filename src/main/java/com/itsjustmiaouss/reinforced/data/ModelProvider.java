package com.itsjustmiaouss.reinforced.data;

import com.google.gson.JsonElement;
import com.itsjustmiaouss.reinforced.block.AbstractReinforcedBlock;
import com.itsjustmiaouss.reinforced.block.ReinforcedBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

class ModelProvider extends FabricModelProvider {

    protected ModelProvider(FabricDataOutput output) {
        super(output);
    }

    private void registerSidedBlock(Consumer<BlockStateSupplier> blockStateCollector, BiConsumer<Identifier, Supplier<JsonElement>> modelCollector, Block block) {
        TextureMap textureMap = new TextureMap().put(TextureKey.BOTTOM, TextureMap.getSubId(block, "_bottom")).put(TextureKey.TOP, TextureMap.getSubId(block, "_top")).put(TextureKey.SIDE, TextureMap.getSubId(block, "_side"));
        blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, Models.CUBE_BOTTOM_TOP.upload(block, textureMap, modelCollector)));
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        ReinforcedBlocks.getReinforcedBlocks().stream()
                .filter(filteredBlock -> filteredBlock instanceof AbstractReinforcedBlock)
                .map(filteredBlock -> (AbstractReinforcedBlock) filteredBlock)
                .forEach(abstractReinforcedBlock -> registerSidedBlock(
                        blockStateModelGenerator.blockStateCollector,
                        blockStateModelGenerator.modelCollector,
                        abstractReinforcedBlock
                ));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }
}
