package com.birdthyme.slotstime;

import com.birdthyme.slotstime.blocks.SlotsBlocks;
import com.birdthyme.slotstime.items.SlotsItems;
import com.birdthyme.slotstime.other.GamblingSounds;
import com.birdthyme.slotstime.other.GamblingStat;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;


@Mod(slotstime.MODID)
public class slotstime
{

    public static final String MODID = "slotstime";

    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);


    public static final RegistryObject<CreativeModeTab> SLOTSTAB = CREATIVE_MODE_TABS.register("slotstime_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> SlotsBlocks.SLOTMACHINE_ITEM.get().getDefaultInstance())
            .title(Component.literal("SlotsTime Tab")).build());


    public slotstime(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();


        modEventBus.addListener(this::commonSetup);


        SlotsBlocks.register(modEventBus);
        SlotsItems.register(modEventBus);
        GamblingStat.register(modEventBus);
        GamblingSounds.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);


        MinecraftForge.EVENT_BUS.register(this);


        modEventBus.addListener(this::addCreative);


        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("SlotsTime Config ");
    }


    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == slotstime.SLOTSTAB.getKey()) {

            //Blocks
            event.accept(SlotsBlocks.SLOTMACHINE_ITEM);


            //Items
            ///Food
            event.accept(SlotsItems.SHRIMP_COCKTAIL);
            event.accept(SlotsItems.SHRIMP);

            ///Coins
            event.accept(SlotsItems.COINMOLD);
            event.accept(SlotsItems.IRONCOIN);
            event.accept(SlotsItems.GOLDCOIN);
            event.accept(SlotsItems.DIAMONDCOIN);
            event.accept(SlotsItems.NETHERITECOIN);
            event.accept(SlotsItems.CCCCCCCCOIN);

        }
    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }


    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }
}
