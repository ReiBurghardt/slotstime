package com.birdthyme.slotstime.blocks.custom;

import com.birdthyme.slotstime.blocks.SlotsBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.apache.commons.lang3.ObjectUtils;

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class SlotMachineEntity extends BlockEntity {
    public long tickCount;
    public int stopTimer;

    public int[] slotNumbers = new int[3];
    Random rand = new Random();
    ItemStack[] RollingItemList = {
            new ItemStack(Items.COAL),
            new ItemStack(Items.IRON_INGOT),
            new ItemStack(Items.GOLD_INGOT),
            new ItemStack(Items.DIAMOND),
            new ItemStack(Items.NETHERITE_INGOT),
            new ItemStack(Items.NETHER_STAR)};

    ArrayList<ItemStack> ItemList = new ArrayList<ItemStack>();






    public SlotMachineEntity(BlockPos pPos, BlockState pBlockState) {
        super(SlotsBlocks.SLOTMACHINE_BE.get(), pPos, pBlockState);
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.slotNumbers = pTag.getIntArray("slotNumbers");
        this.tickCount = pTag.getLong("TickCount");
        this.stopTimer = pTag.getInt("StopTimer");
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putIntArray("slotNumbers", this.slotNumbers);
        pTag.putLong("TickCount", this.tickCount);
        pTag.putInt("StopTimer", this.stopTimer);
    }

    public ArrayList<ItemStack> SlotItem(Level pLevel, BlockPos pPos, BlockState pState, BlockEntity pBlockEntity){

        if(!pLevel.isClientSide){
            if (this.ItemList.isEmpty()) {
                this.ItemList.add(new ItemStack(Items.AIR));
                this.ItemList.add(new ItemStack(Items.AIR));
                this.ItemList.add(new ItemStack(Items.AIR));
            }


            ++this.tickCount;
            ++this.stopTimer;


            System.out.println(this.tickCount + " " + this.stopTimer);

            if(this.tickCount >= 10 && this.stopTimer <= 20){
                for(int loop = 0; loop <= ItemList.size()-1; loop++){
                    int randItem = rand.nextInt(0, RollingItemList.length);
                    this.ItemList.set(loop, RollingItemList[randItem]);
                }
                this.tickCount = 0;
            }


            if(this.stopTimer > 20 && this.stopTimer <= 25) {
                for (int loop = 0; loop <= slotNumbers.length - 1; loop++) {
                    switch (slotNumbers[loop]) {
                        case 1 -> this.ItemList.add(new ItemStack(Items.IRON_INGOT));
                        case 2 -> this.ItemList.add(new ItemStack(Items.GOLD_INGOT));
                        case 3 -> this.ItemList.add(new ItemStack(Items.DIAMOND));
                        case 4 -> this.ItemList.add(new ItemStack(Items.NETHERITE_INGOT));
                        case 5 -> this.ItemList.add(new ItemStack(Items.NETHER_STAR));
                        default -> this.ItemList.add(new ItemStack(Items.COAL));
                    }
                }
                return this.ItemList;
            }

            else if (this.stopTimer > 25) {
                this.tickCount = 0;
                this.stopTimer = 0;


                BlockState newBlockState = pState.setValue(SlotMachine.IS_GAMBLING, false);
                BlockState otherState = pState.setValue(SlotMachine.IS_GAMBLING, false).setValue(SlotMachine.HALF, DoubleBlockHalf.LOWER);

                pLevel.setBlock(pPos, newBlockState, 3);
                if(pLevel.getBlockState(pPos).getValue(SlotMachine.HALF) == DoubleBlockHalf.UPPER){
                    pLevel.setBlock(pPos.below(), otherState, 3);
                }

            }
        }


        return this.ItemList;
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


    public void tick(Level pLevel1,BlockPos pPos,BlockState pState1,BlockEntity pBlockEntity){
        SlotItem(pLevel1, pPos, pState1, pBlockEntity);
    }

}
