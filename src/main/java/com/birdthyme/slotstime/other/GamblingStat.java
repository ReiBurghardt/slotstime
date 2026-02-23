package com.birdthyme.slotstime.other;

import com.birdthyme.slotstime.slotstime;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GamblingStat extends Stats {
    public static final DeferredRegister<ResourceLocation> STATS = DeferredRegister.create(Registries.CUSTOM_STAT, slotstime.MODID);

    public static final RegistryObject<ResourceLocation> GAMBLED =
            STATS.register("gambled", () -> new ResourceLocation(slotstime.MODID, "gambled"));

    public static final RegistryObject<ResourceLocation> TIMES_WON =
            STATS.register("times_won", () -> new ResourceLocation(slotstime.MODID, "times_won"));

    public static void register(IEventBus eventBus) {
        STATS.register(eventBus);
    }


}
