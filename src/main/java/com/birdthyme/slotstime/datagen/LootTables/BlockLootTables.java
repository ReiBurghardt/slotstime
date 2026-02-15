package com.birdthyme.slotstime.datagen.LootTables;

import com.birdthyme.slotstime.blocks.SlotsBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class BlockLootTables extends BlockLootSubProvider {

    public BlockLootTables()  {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(SlotsBlocks.SLOTMACHINE.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return SlotsBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
