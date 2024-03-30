package com.itsjustmiaouss.reinforced.block.reinforced;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class ReinforcedStone extends ReinforcedBlock implements SilkTouchable {

    public ReinforcedStone() {
        super(Blocks.STONE);
    }

    @Override
    public Block withoutSilkTouchDrop() {
        return Blocks.COBBLESTONE;
    }
}
