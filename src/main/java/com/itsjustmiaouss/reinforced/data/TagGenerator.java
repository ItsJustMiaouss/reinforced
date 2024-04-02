package com.itsjustmiaouss.reinforced.data;

import com.itsjustmiaouss.reinforced.block.AbstractReinforcedBlock;
import com.itsjustmiaouss.reinforced.block.ReinforcedBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

class TagGenerator extends FabricTagProvider.BlockTagProvider {

    protected TagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        ReinforcedBlocks.getReinforcedBlocks().stream()
                .filter(filteredBlock -> filteredBlock instanceof AbstractReinforcedBlock)
                .map(filteredBlock -> (AbstractReinforcedBlock) filteredBlock)
                .forEach(abstractReinforcedBlock -> {
                    this.getOrCreateTagBuilder(BlockTags.WITHER_IMMUNE).add(abstractReinforcedBlock).setReplace(false);
                    this.getOrCreateTagBuilder(BlockTags.DRAGON_IMMUNE).add(abstractReinforcedBlock).setReplace(false);
                    this.getOrCreateTagBuilder(BlockTags.OCCLUDES_VIBRATION_SIGNALS).add(abstractReinforcedBlock).setReplace(false);
                    this.getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(abstractReinforcedBlock).setReplace(false);
                    this.getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL).add(abstractReinforcedBlock).setReplace(false);
                });
    }

}
