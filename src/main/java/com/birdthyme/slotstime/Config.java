package com.birdthyme.slotstime;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = slotstime.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();


    private static final ForgeConfigSpec.IntValue IRON_COIN_LUCK = BUILDER
            .comment("The luck value of the iron coin, Value can be >= 0")
            .comment("Default value = 1")
            .defineInRange("ironCoinLuck", 1, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue GOLD_COIN_LUCK = BUILDER
            .comment("\n")
            .comment("The luck value of the gold coin, Value can be >= 0")
            .comment("Default value = 5")
            .defineInRange("goldCoinLuck", 5, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue DIAMOND_COIN_LUCK = BUILDER
            .comment("\n")
            .comment("The luck value of the diamond coin, Value can be >= 0")
            .comment("Default value = 20")
            .defineInRange("diamondCoinLuck", 20, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue NETHERITE_COIN_LUCK = BUILDER
            .comment("\n")
            .comment("The luck value of the netherite coin, Value can be >= 0")
            .comment("Default value = 100")
            .defineInRange("netheriteCoinLuck", 100, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue CCCCCCC_COIN_LUCK = BUILDER
            .comment("\n")
            .comment("The luck value of the ccccccc coin, Value can be >= 0")
            .comment("Default value = 1000")
            .defineInRange("cccccccCoinLuck", 1000, 0, Integer.MAX_VALUE);



    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int ironCoinLuck;
    public static int goldCoinLuck;
    public static int diamondCoinLuck;
    public static int netheriteCoinLuck;
    public static int cccccccCoinLuck;





    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        ironCoinLuck = IRON_COIN_LUCK.get();
        goldCoinLuck = GOLD_COIN_LUCK.get();
        diamondCoinLuck = DIAMOND_COIN_LUCK.get();
        netheriteCoinLuck = NETHERITE_COIN_LUCK.get();
        cccccccCoinLuck = CCCCCCC_COIN_LUCK.get();
    }
}
