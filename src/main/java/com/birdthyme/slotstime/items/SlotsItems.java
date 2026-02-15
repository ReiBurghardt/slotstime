package com.birdthyme.slotstime.items;

import com.birdthyme.slotstime.slotstime;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SlotsItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, slotstime.MODID);

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
                            .build())));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
