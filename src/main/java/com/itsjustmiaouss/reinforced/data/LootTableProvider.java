package com.itsjustmiaouss.reinforced.data;

import com.itsjustmiaouss.reinforced.block.AbstractReinforcedBlock;
import com.itsjustmiaouss.reinforced.block.ReinforcedBlocks;
import com.itsjustmiaouss.reinforced.block.reinforced.SilkTouchable;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.loot.LootTable;

class LootTableProvider extends FabricBlockLootTableProvider {

    protected LootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        ReinforcedBlocks.getReinforcedBlocks().stream()
                .filter(filteredBlock -> filteredBlock instanceof AbstractReinforcedBlock)
                .map(filteredBlock -> (AbstractReinforcedBlock) filteredBlock)
                .forEach(abstractReinforcedBlock -> {
                    LootTable.Builder drops = abstractReinforcedBlock instanceof SilkTouchable
                            ? this.drops(abstractReinforcedBlock.asOriginalBlock(),
                            ((SilkTouchable) abstractReinforcedBlock).withoutSilkTouchDrop())
                            : this.drops(abstractReinforcedBlock.asOriginalBlock());

                    this.addDrop(abstractReinforcedBlock, drops);
                });
    }
}
