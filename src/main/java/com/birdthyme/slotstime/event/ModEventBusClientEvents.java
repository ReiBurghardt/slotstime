package com.birdthyme.slotstime.event;

import com.birdthyme.slotstime.blocks.SlotsBlocks;
import com.birdthyme.slotstime.blocks.custom.SlotMachineRenderer;
import com.birdthyme.slotstime.slotstime;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = slotstime.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(SlotsBlocks.SLOTMACHINE_BE.get(), SlotMachineRenderer::new);
    }
}
