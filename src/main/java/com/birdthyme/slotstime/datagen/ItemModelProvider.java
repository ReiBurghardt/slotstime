package com.birdthyme.slotstime.datagen;

import com.birdthyme.slotstime.items.SlotsItems;
import com.birdthyme.slotstime.slotstime;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {
    public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, slotstime.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(SlotsItems.SHRIMP_COCKTAIL);
        simpleItem(SlotsItems.SHRIMP);
    }
    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(slotstime.MODID,"item/" + item.getId().getPath()));
    }
}
