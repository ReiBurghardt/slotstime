package com.birdthyme.slotstime.other;

import com.birdthyme.slotstime.slotstime;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GamblingSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, slotstime.MODID);


    public static final RegistryObject<SoundEvent> SLOTMACHINE_ROLL = SOUND_EVENTS.register("slotmachine_roll",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(slotstime.MODID, "slotmachine_roll")));

    public static final RegistryObject<SoundEvent> SLOTMACHINE_LOSE = SOUND_EVENTS.register("slotmachine_lose",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(slotstime.MODID, "slotmachine_lose")));

    public static final RegistryObject<SoundEvent> IRON_WIN = SOUND_EVENTS.register("iron_win",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(slotstime.MODID, "iron_win")));
    public static final RegistryObject<SoundEvent> GOLD_WIN = SOUND_EVENTS.register("gold_win",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(slotstime.MODID, "gold_win")));
    public static final RegistryObject<SoundEvent> DIAMOND_WIN = SOUND_EVENTS.register("diamond_win",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(slotstime.MODID, "diamond_win")));
    public static final RegistryObject<SoundEvent> NETHERITE_WIN = SOUND_EVENTS.register("netherite_win",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(slotstime.MODID, "netherite_win")));
    public static final RegistryObject<SoundEvent> C_WIN = SOUND_EVENTS.register("c_win",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(slotstime.MODID, "c_win")));



    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
