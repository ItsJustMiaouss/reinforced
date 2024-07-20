package com.itsjustmiaouss.reinforced.block.entity;

import com.itsjustmiaouss.reinforced.Reinforced;
import com.itsjustmiaouss.reinforced.block.ReinforcedBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ReinforcedBlockEntities {


    public static final BlockEntityType<ReinforcedBlockEntity> REINFORCED_BLOCK_ENTITY =
            registerReinforcedBlockEntity(
                    ReinforcedBlocks.REINFORCED_STONE,
                    ReinforcedBlocks.REINFORCED_DEEPSLATE_BRICKS,
                    ReinforcedBlocks.REINFORCED_STONE_BRICKS
            );

    private static BlockEntityType<ReinforcedBlockEntity> registerReinforcedBlockEntity(Block... block) {
        return Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                Reinforced.of("reinforced_block_entity"),
                FabricBlockEntityTypeBuilder.create(ReinforcedBlockEntity::new, block).build()
        );
    }

    public static void register() {
        Reinforced.LOGGER.info("Registered block entities");
    }

}
