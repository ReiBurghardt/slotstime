package com.birdthyme.slotstime.datagen;

import com.birdthyme.slotstime.blocks.SlotsBlocks;
import com.birdthyme.slotstime.slotstime;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class BlockStateProvider extends net.minecraftforge.client.model.generators.BlockStateProvider {
    public BlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, slotstime.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {


    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
