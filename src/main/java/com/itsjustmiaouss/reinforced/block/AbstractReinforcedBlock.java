package com.itsjustmiaouss.reinforced.block;

import com.itsjustmiaouss.reinforced.block.entity.ReinforcedBlockEntity;
import com.itsjustmiaouss.reinforced.block.reinforced.SilkTouchable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractReinforcedBlock extends Block implements BlockEntityProvider {

    private final Block originalBlock;

    public AbstractReinforcedBlock(Block originalBlock) {
        super(Settings.copy(originalBlock).dropsNothing().allowsSpawning(Blocks::never));

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
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.afterBreak(world, player, pos, state, blockEntity, tool);

        if (player.isCreative()) return;
        ItemStack stackInHand = player.getStackInHand(player.getActiveHand());
        boolean breakerHasSilkTouch = EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stackInHand) > 0;

        Block blockToDrop = this.asOriginalBlock();
        if (this instanceof SilkTouchable silkTouchableBlock && !breakerHasSilkTouch) {
            blockToDrop = silkTouchableBlock.withoutSilkTouchDrop();
        }

        ItemEntity stackToDrop = new ItemEntity(world,
                pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f,
                new ItemStack(blockToDrop)
        );

        world.spawnEntity(stackToDrop);
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
