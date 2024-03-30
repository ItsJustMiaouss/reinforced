package com.itsjustmiaouss.reinforced.data;

import com.google.gson.JsonElement;
import com.itsjustmiaouss.reinforced.block.AbstractReinforcedBlock;
import com.itsjustmiaouss.reinforced.block.ReinforcedBlocks;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ReinforcedDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(TagGenerator::new);
        pack.addProvider(ModelGenerator::new);
    }

    private static class TagGenerator extends FabricTagProvider.BlockTagProvider {

        public TagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup arg) {
            ReinforcedBlocks.getReinforcedBlocks().stream()
                    .filter(filteredBlock -> filteredBlock instanceof AbstractReinforcedBlock)
                    .map(filteredBlock -> (AbstractReinforcedBlock) filteredBlock)
                    .forEach(abstractReinforcedBlock -> {
                        getOrCreateTagBuilder(BlockTags.WITHER_IMMUNE).add(abstractReinforcedBlock).setReplace(false);
                        getOrCreateTagBuilder(BlockTags.DRAGON_IMMUNE).add(abstractReinforcedBlock).setReplace(false);
                        getOrCreateTagBuilder(BlockTags.OCCLUDES_VIBRATION_SIGNALS).add(abstractReinforcedBlock).setReplace(false);
                    });
        }
    }

    private static class ModelGenerator extends FabricModelProvider {

        public ModelGenerator(FabricDataOutput output) {
            super(output);
        }


        public final void registerSidedBlock(Consumer<BlockStateSupplier> blockStateCollector, BiConsumer<Identifier, Supplier<JsonElement>> modelCollector, Block block) {
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
}
