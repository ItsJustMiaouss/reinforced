package com.itsjustmiaouss.reinforced.block;

import com.itsjustmiaouss.reinforced.Reinforced;
import com.itsjustmiaouss.reinforced.block.reinforced.ReinforcedBlock;
import com.itsjustmiaouss.reinforced.block.reinforced.ReinforcedStone;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.HashSet;
import java.util.Set;

public class ReinforcedBlocks {

    private static final Set<AbstractReinforcedBlock> reinforcedBlocks = new HashSet<>();

    public static final Block REINFORCED_STONE = registerReinforcedBlock("reinforced_stone", new ReinforcedStone());
    public static final Block REINFORCED_DEEPSLATE_BRICKS = registerReinforcedBlock("reinforced_deepslate_bricks", new ReinforcedBlock(Blocks.DEEPSLATE_BRICKS));
    public static final Block REINFORCED_STONE_BRICKS = registerReinforcedBlock("reinforced_stone_bricks", new ReinforcedBlock(Blocks.STONE_BRICKS));

    /**
     * Get a Set of the registered reinforced blocks.
     *
     * @return An instance of {@link AbstractReinforcedBlock}.
     */
    public static Set<AbstractReinforcedBlock> getReinforcedBlocks() {
        return reinforcedBlocks;
    }

    /**
     * Register a Reinforced Block, an instance of {@link AbstractReinforcedBlock}.
     */
    private static Block registerReinforcedBlock(String identifier, AbstractReinforcedBlock block) {
        registerBlock(identifier, block);

        try {
            reinforcedBlocks.add(block);
        } catch (RuntimeException e) {
            Reinforced.LOGGER.error("Failed to register reinforced block '{}'", identifier);
        }

        return block;
    }

    /**
     * Register a classic block.
     */
    private static Block registerBlock(String identifier, Block block) {
        Block registeredBlock = Registry.register(Registries.BLOCK, Reinforced.of(identifier), block);
        Registry.register(Registries.ITEM, Reinforced.of(identifier), new BlockItem(registeredBlock, new FabricItemSettings()));

        return registeredBlock;
    }

    public static void register() {
        Reinforced.LOGGER.info("Registered blocks");
    }

}
