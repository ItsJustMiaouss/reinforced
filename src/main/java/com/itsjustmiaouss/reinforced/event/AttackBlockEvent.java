package com.itsjustmiaouss.reinforced.event;

import com.itsjustmiaouss.reinforced.block.entity.ReinforcedBlockEntity;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class AttackBlockEvent implements AttackBlockCallback {

    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockPos pos, Direction direction) {
        if (player.isCreative()) return ActionResult.PASS;

        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof ReinforcedBlockEntity reinforcedBlockEntity) {
            if (reinforcedBlockEntity.getOwnerUuid() == null || !reinforcedBlockEntity.getOwnerUuid().equals(player.getUuid())) {
                return ActionResult.FAIL;
            }
        }

        return ActionResult.PASS;
    }

}
