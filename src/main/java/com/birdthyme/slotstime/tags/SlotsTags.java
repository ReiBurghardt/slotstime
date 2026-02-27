package com.birdthyme.slotstime.tags;

import com.birdthyme.slotstime.slotstime;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class SlotsTags {
    public static class Items{
        public static TagKey<Item> tag(String name){
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(slotstime.MODID, name));
        }
    }
}
