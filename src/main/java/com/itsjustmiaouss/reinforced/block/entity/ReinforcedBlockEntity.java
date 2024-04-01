package com.itsjustmiaouss.reinforced.block.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ReinforcedBlockEntity extends BlockEntity {

    private UUID ownerUuid;
    private String ownerPlayer;

    public ReinforcedBlockEntity(BlockPos pos, BlockState state) {
        super(ReinforcedBlockEntities.REINFORCED_BLOCK_ENTITY, pos, state);
    }

    public void setOwner(PlayerEntity owner) {
        this.ownerUuid = owner.getUuid();
        this.ownerPlayer = owner.getName().getString();

        updateListeners();
    }

    @Nullable
    public UUID getOwnerUuid() {
        return this.ownerUuid;
    }

    @Nullable
    public String getOwnerPlayer() {
        return this.ownerPlayer;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        if(this.ownerUuid != null && this.ownerPlayer != null) {
            nbt.putUuid("ownerUuid", this.ownerUuid);
            nbt.putString("ownerPlayer", this.ownerPlayer);
        }

        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        if (!nbt.contains("ownerUuid") || !nbt.contains("ownerPlayer")) return;

        this.ownerUuid = nbt.getUuid("ownerUuid");
        this.ownerPlayer = nbt.getString("ownerPlayer");
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    private void updateListeners() {
        this.markDirty();
        if (this.world != null) {
            this.world.updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
        }
    }
}
