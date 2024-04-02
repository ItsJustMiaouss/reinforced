package com.itsjustmiaouss.reinforced.block;

import com.itsjustmiaouss.reinforced.block.entity.ReinforcedBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractReinforcedBlock extends Block implements BlockEntityProvider {

    private final Block originalBlock;

    public AbstractReinforcedBlock(Block originalBlock) {
        super(Settings.copy(originalBlock).allowsSpawning(Blocks::never).strength(6.5f, 3600000.0f));

        this.originalBlock = originalBlock;
    }

    public Block asOriginalBlock() {
        return this.originalBlock;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ReinforcedBlockEntity(pos, state);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        ReinforcedBlockEntity reinforcedBlockEntity = (ReinforcedBlockEntity) world.getBlockEntity(pos);
        if (reinforcedBlockEntity == null || placer == null) return;
        if(!(placer instanceof PlayerEntity player)) return;

        reinforcedBlockEntity.setOwner(player);

        super.onPlaced(world, pos, state, placer, itemStack);
    }
}
