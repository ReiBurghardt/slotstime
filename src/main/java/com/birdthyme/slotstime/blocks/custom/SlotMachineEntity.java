package com.birdthyme.slotstime.blocks.custom;

import com.birdthyme.slotstime.blocks.SlotsBlocks;
import com.birdthyme.slotstime.other.GamblingSounds;
import com.birdthyme.slotstime.other.GamblingStat;
import com.birdthyme.slotstime.slotstime;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.commands.GiveCommand;
import net.minecraft.server.commands.LootCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.PlayLevelSoundEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.apache.commons.lang3.ObjectUtils;

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class SlotMachineEntity extends BlockEntity {
    public boolean playedStopSound0 = false;
    public boolean playedStopSound1 = false;
    public boolean playedStopSound2 = false;
    public UUID playerUUID = UUID.randomUUID();
    public boolean gamblingOutput = false;
    public Random random = new Random();
    public long tickCount;
    public int rollStop = 100;
    public int gamblingStop = 120;
    public int[] slotNumbers = new int[3];
    public ItemStack[] slotItems = new ItemStack[3];
    ItemStack[] RollingItemList = {
            new ItemStack(Items.COAL),
            new ItemStack(Items.IRON_INGOT),
            new ItemStack(Items.GOLD_INGOT),
            new ItemStack(Items.DIAMOND),
            new ItemStack(Items.NETHERITE_INGOT),
            new ItemStack(Items.NETHER_STAR)};







    public SlotMachineEntity(BlockPos pPos, BlockState pBlockState) {
        super(SlotsBlocks.SLOTMACHINE_BE.get(), pPos, pBlockState);
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.slotNumbers = pTag.getIntArray("slotNumbers");
        this.tickCount = pTag.getLong("TickCount");
        this.playerUUID = pTag.getUUID("Gambler");
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putIntArray("slotNumbers", this.slotNumbers);
        pTag.putLong("TickCount", this.tickCount);
        pTag.putUUID("Gambler", playerUUID);
    }

    public void SlotItem(Level pLevel, BlockPos pPos, BlockState pState, BlockEntity pBlockEntity){
        ++this.tickCount;


        if (this.tickCount % 5 == 0 && this.tickCount < rollStop || this.tickCount == 1){
            for(int loop = 0; loop <= 2; loop++){
                int randInt = random.nextInt(0, 6);
                this.slotItems[loop] = RollingItemList[randInt];
            }


            if(pLevel.isClientSide){
                pLevel.playLocalSound(pPos, GamblingSounds.SLOTMACHINE_ROLL.get(), SoundSource.BLOCKS, 0.5f, 1f, false);
            }
        }

        else if (this.tickCount >= rollStop && this.tickCount < gamblingStop){

            if(tickCount >= rollStop && !this.playedStopSound0){
                this.slotItems[2] = RollingItemList[slotNumbers[2]];
                this.playedStopSound0 = true;
                pLevel.playLocalSound(pPos, GamblingSounds.SLOTMACHINE_ROLL.get(), SoundSource.BLOCKS, 0.5f, 0.5f, false);
            }
            if(tickCount >= rollStop + 5 && !this.playedStopSound1){
                this.slotItems[1] = RollingItemList[slotNumbers[1]];
                this.playedStopSound1 = true;
                pLevel.playLocalSound(pPos, GamblingSounds.SLOTMACHINE_ROLL.get(), SoundSource.BLOCKS, 0.5f, 0.5f, false);
            }
            if(tickCount >= rollStop + 10 && !this.playedStopSound2){
                this.slotItems[0] = RollingItemList[slotNumbers[0]];
                this.playedStopSound2 = true;
                pLevel.playLocalSound(pPos, GamblingSounds.SLOTMACHINE_ROLL.get(), SoundSource.BLOCKS, 0.5f, 0.5f, false);
            }



            if(this.slotNumbers[0] != 0 && this.slotNumbers[0] == this.slotNumbers[1] && this.slotNumbers[1] == this.slotNumbers[2] && !this.gamblingOutput && tickCount > rollStop +11){
                this.gamblingOutput = true;
                Player gamblingPlayer = pLevel.getPlayerByUUID(playerUUID);
                gamblingPlayer.awardStat(GamblingStat.GAMBLED.get());
                ResourceLocation resourcelocation;
                switch (slotNumbers[0]){
                    case 1:
                        resourcelocation = ResourceLocation.fromNamespaceAndPath(slotstime.MODID, "iron_loot");
                        pLevel.playLocalSound(pPos, GamblingSounds.IRON_WIN.get(), SoundSource.BLOCKS, 0.5f, 1f, false);
                        break;
                    case 2:
                        resourcelocation = ResourceLocation.fromNamespaceAndPath(slotstime.MODID, "gold_loot");
                        pLevel.playLocalSound(pPos, GamblingSounds.GOLD_WIN.get(), SoundSource.BLOCKS, 1f, 1f, false);
                        break;
                    case 3:
                        resourcelocation = ResourceLocation.fromNamespaceAndPath(slotstime.MODID, "diamond_loot");
                        pLevel.playLocalSound(pPos, GamblingSounds.DIAMOND_WIN.get(), SoundSource.BLOCKS, 1f, 1f, false);
                        break;
                    case 4:
                        resourcelocation = ResourceLocation.fromNamespaceAndPath(slotstime.MODID, "netherite_loot");
                        pLevel.playLocalSound(pPos, GamblingSounds.NETHERITE_WIN.get(), SoundSource.BLOCKS, 1f, 1f, false);
                        break;
                    case 5:
                        resourcelocation = ResourceLocation.fromNamespaceAndPath(slotstime.MODID, "ccccccc_loot");
                        pLevel.playLocalSound(pPos, GamblingSounds.C_WIN.get(), SoundSource.BLOCKS, 1f, 1f, false);
                        break;
                    default:
                        resourcelocation = ResourceLocation.fromNamespaceAndPath(slotstime.MODID, "test_loot");
                        break;
                }
                if(!pLevel.isClientSide) {
                    LootTable loottable = pLevel.getServer().getLootData().getLootTable(resourcelocation);
                    LootParams.Builder lootparams$builder = (new LootParams.Builder((ServerLevel) pLevel)).withParameter(LootContextParams.ORIGIN, gamblingPlayer.position());
                    LootParams lootparams = lootparams$builder.create(LootContextParamSets.CHEST);
                    ObjectArrayList<ItemStack> randomLoot = loottable.getRandomItems(lootparams, 0);

                    for(int loop = 0; loop <= randomLoot.size()-1; loop++){
                        ItemEntity itemEntity = gamblingPlayer.drop(randomLoot.get(loop), false);
                        if (itemEntity != null) {
                            itemEntity.setNoPickUpDelay();
                            itemEntity.setTarget(playerUUID);
                        }
                    }
                }

            }

            else if(tickCount > rollStop + 11){
                if(pLevel.isClientSide && !this.gamblingOutput){
                    this.gamblingOutput = true;
                    pLevel.playLocalSound(pPos, GamblingSounds.SLOTMACHINE_LOSE.get(), SoundSource.BLOCKS, 1f, 0.5f, false);
                }
            }

        }

        else if (this.tickCount > gamblingStop) {
            this.tickCount = 0;
            this.gamblingOutput = false;
            this.playedStopSound0 = false;
            this.playedStopSound1 = false;
            this.playedStopSound2 = false;

            BlockState newBlockState = pState.setValue(SlotMachine.IS_GAMBLING, false);
            BlockState otherState = pState.setValue(SlotMachine.IS_GAMBLING, false).setValue(SlotMachine.HALF, DoubleBlockHalf.LOWER);

            pLevel.setBlock(pPos, newBlockState, 3);
            if(pLevel.getBlockState(pPos).getValue(SlotMachine.HALF) == DoubleBlockHalf.UPPER){
                pLevel.setBlock(pPos.below(), otherState, 3);
            }
        }
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
