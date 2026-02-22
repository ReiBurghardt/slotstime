package com.birdthyme.slotstime.blocks.custom;

import com.birdthyme.slotstime.blocks.SlotsBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class SlotMachineEntity extends BlockEntity {
    public long tickCount;
    public int[] slotNumbers = new int[3];





    public SlotMachineEntity(BlockPos pPos, BlockState pBlockState) {
        super(SlotsBlocks.SLOTMACHINE_BE.get(), pPos, pBlockState);
    }

    public ArrayList<ItemStack> SlotItem(){
        ArrayList<ItemStack> ItemList = new ArrayList<ItemStack>();
        ItemList.add(new ItemStack(Items.DIAMOND));
        ItemList.add(new ItemStack(Items.DIAMOND));
        ItemList.add(new ItemStack(Items.DIAMOND));

        return ItemList;
    }




    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.slotNumbers = pTag.getIntArray("slotNumbers");
        this.tickCount = pTag.getLong("TickCount");
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putIntArray("slotNumbers", this.slotNumbers);
        pTag.putLong("TickCount", this.tickCount);
    }



    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }
}
