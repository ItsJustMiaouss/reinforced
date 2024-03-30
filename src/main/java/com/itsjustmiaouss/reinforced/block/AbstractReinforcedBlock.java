package com.itsjustmiaouss.reinforced.block;

import com.itsjustmiaouss.reinforced.block.entity.ReinforcedBlockEntity;
import com.itsjustmiaouss.reinforced.block.reinforced.SilkTouchable;
import com.itsjustmiaouss.reinforced.item.BreakerItem;
import com.itsjustmiaouss.reinforced.item.ReinforcedItems;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public abstract class AbstractReinforcedBlock extends Block implements BlockEntityProvider {

    private final Block originalBlock;

    public AbstractReinforcedBlock(Block originalBlock) {
        super(Settings.copy(originalBlock).dropsNothing().strength(-1.0f, 3600000.0f).allowsSpawning(Blocks::never));

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
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (world.isClient()) return;

        Hand activeHand = player.getActiveHand();
        ItemStack stackInHand = player.getStackInHand(activeHand);
        if (!stackInHand.isOf(ReinforcedItems.BREAKER_ITEM)) return;

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof ReinforcedBlockEntity reinforcedBlockEntity)) return;

        UUID uuid = player.getUuid();
        if (reinforcedBlockEntity.getOwnerUuid() == null) return;
        if (!reinforcedBlockEntity.getOwnerUuid().equals(uuid)) return;

        BreakerItem breakerItem = (BreakerItem) stackInHand.getItem();
        if(player.getItemCooldownManager().isCoolingDown(breakerItem)) return;

        player.getItemCooldownManager().set(breakerItem, 6);
        world.breakBlock(pos, false);

        if (!player.isCreative()) {
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

            stackInHand.damage(1, player, playerEntity -> playerEntity.sendToolBreakStatus(activeHand));
        }

        super.onBlockBreakStart(state, world, pos, player);
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
