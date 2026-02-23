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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.ObjectUtils;

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class SlotMachineEntity extends BlockEntity {
    public long tickCount;
    public int[] slotNumbers = new int[3];



    public SlotMachineEntity(BlockPos pPos, BlockState pBlockState) {
        super(SlotsBlocks.SLOTMACHINE_BE.get(), pPos, pBlockState);
    }

    public ArrayList<ItemStack> SlotItem(){
        ArrayList<ItemStack> ItemList = new ArrayList<ItemStack>();

        for(int loop = 0; loop <= slotNumbers.length-1; loop++){
            if(slotNumbers[loop] == 1){
                ItemList.add(new ItemStack(Items.IRON_INGOT));
            } else if (slotNumbers[loop] == 2) {
                ItemList.add(new ItemStack(Items.GOLD_INGOT));
            } else if (slotNumbers[loop] == 3) {
                ItemList.add(new ItemStack(Items.DIAMOND));
            } else if (slotNumbers[loop] == 4) {
                ItemList.add(new ItemStack(Items.NETHERITE_INGOT));
            } else if (slotNumbers[loop] == 5) {
                ItemList.add(new ItemStack(Items.NETHER_STAR));
            } else{
                ItemList.add(new ItemStack(Items.COAL));
            }

        }
        if(ItemList.isEmpty()){
            ItemList.add(new ItemStack(Items.COAL));
            ItemList.add(new ItemStack(Items.COAL));
            ItemList.add(new ItemStack(Items.COAL));
        }
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
