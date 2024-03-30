package com.itsjustmiaouss.reinforced.item;

import com.itsjustmiaouss.reinforced.block.AbstractReinforcedBlock;
import com.itsjustmiaouss.reinforced.block.ReinforcedBlocks;
import com.itsjustmiaouss.reinforced.block.entity.ReinforcedBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Optional;

public class ReinforcerItem extends Item {

    public ReinforcerItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        PlayerEntity player = context.getPlayer();
        ItemStack stack = context.getStack();

        if (player == null) return ActionResult.PASS;

        ArrayList<Block> reinforcedBlocks = ReinforcedBlocks.getReinforcedBlocks();

        Optional<AbstractReinforcedBlock> reinforcedBlock = reinforcedBlocks.stream()
                .filter(filteredBlock ->
                        filteredBlock instanceof AbstractReinforcedBlock b && b.asOriginalBlock().equals(block)
                )
                .map(filteredBlock -> (AbstractReinforcedBlock) filteredBlock)
                .findFirst();

        if (reinforcedBlock.isEmpty()) return ActionResult.PASS;

        world.setBlockState(blockPos, reinforcedBlock.get().getDefaultState());
        ReinforcedBlockEntity blockEntity = (ReinforcedBlockEntity) world.getBlockEntity(blockPos);
        if (blockEntity == null) return ActionResult.PASS;

        blockEntity.setOwner(player);

        stack.damage(1, player, playerEntity -> playerEntity.sendToolBreakStatus(context.getHand()));
        player.playSound(SoundEvents.BLOCK_NETHER_BRICKS_PLACE, 1.0f, 1.0f);

        return ActionResult.SUCCESS;
    }
}
