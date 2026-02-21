package com.birdthyme.slotstime.blocks;

import com.birdthyme.slotstime.blocks.custom.SlotMachine;
import com.birdthyme.slotstime.blocks.custom.SlotMachineEntity;
import com.birdthyme.slotstime.slotstime;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class SlotsBlocks {


    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, slotstime.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, slotstime.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, slotstime.MODID);


    public static final RegistryObject<Block> SLOTMACHINE = BLOCKS.register("slotmachine", () ->
            new SlotMachine(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .lightLevel((plight) -> 5)
                    .noOcclusion()
                    .pushReaction(PushReaction.BLOCK)
                    .explosionResistance(2147483647f)));

    public static final RegistryObject<BlockEntityType<SlotMachineEntity>> SLOTMACHINE_BE =
            BLOCK_ENTITIES.register("slotmachine_be", () ->
                    BlockEntityType.Builder.of(SlotMachineEntity::new,
                            SlotsBlocks.SLOTMACHINE.get()).build(null));

    public static final RegistryObject<Item> SLOTMACHINE_ITEM = ITEMS.register("slotmachine", () ->
            new DoubleHighBlockItem(SLOTMACHINE.get(), new Item.Properties()));





    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        BLOCK_ENTITIES.register(eventBus);
    }


}
