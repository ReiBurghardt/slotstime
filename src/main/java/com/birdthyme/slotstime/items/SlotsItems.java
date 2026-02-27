package com.birdthyme.slotstime.items;

import com.birdthyme.slotstime.slotstime;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SlotsItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, slotstime.MODID);


    //Foods
    public static final RegistryObject<Item> SHRIMP_COCKTAIL = ITEMS.register("shrimp_cocktail", () ->
            new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(4)
                            .saturationMod(3f)
                            .build())));

    public static final RegistryObject<Item> SHRIMP = ITEMS.register("shrimp", () ->
            new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(1)
                            .saturationMod(1f)
                            .fast()
                            .build())));

    //Coins
    public static final RegistryObject<Item> COINMOLD = ITEMS.register("coin_mold", () ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> IRONCOIN = ITEMS.register("iron_coin", () ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> GOLDCOIN = ITEMS.register("gold_coin", () ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> DIAMONDCOIN = ITEMS.register("diamond_coin", () ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> NETHERITECOIN = ITEMS.register("netherite_coin", () ->
            new Item(new Item.Properties().fireResistant().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> CCCCCCCCOIN = ITEMS.register("ccccccc_coin", () ->
            new Item(new Item.Properties().fireResistant().rarity(Rarity.EPIC)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
