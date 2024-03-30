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
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ReinforcedBlocks {

    private static final ArrayList<Block> reinforcedBlocks = new ArrayList<>();

    public static final Block REINFORCED_STONE = registerBlock("reinforced_stone", new ReinforcedStone());
    public static final Block REINFORCED_DEEPSLATE_BRICKS = registerBlock("reinforced_deepslate_bricks", new ReinforcedBlock(Blocks.DEEPSLATE_BRICKS));
    public static final Block REINFORCED_STONE_BRICKS = registerBlock("reinforced_stone_bricks", new ReinforcedBlock(Blocks.STONE_BRICKS));

    @NotNull
    public static ArrayList<Block> getReinforcedBlocks() {
        return reinforcedBlocks;
    }

    private static Block registerBlock(String identifier, Block block) {
        Block registeredBlock = Registry.register(Registries.BLOCK, new Identifier(Reinforced.MOD_ID, identifier), block);
        Registry.register(Registries.ITEM, new Identifier(Reinforced.MOD_ID, identifier), new BlockItem(registeredBlock, new FabricItemSettings()));

        reinforcedBlocks.add(registeredBlock);

        return registeredBlock;
    }

    public static void registerBlocks() {
        Reinforced.LOGGER.info("Registered blocks");
    }

}
