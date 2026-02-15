package com.birdthyme.slotstime.datagen;

import com.birdthyme.slotstime.items.SlotsItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider {
    public RecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> p_251297_) {

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, SlotsItems.SHRIMP_COCKTAIL.get())
                .requires(SlotsItems.SHRIMP.get())
                .requires(SlotsItems.SHRIMP.get())
                .requires(SlotsItems.SHRIMP.get())
                .requires(SlotsItems.SHRIMP.get())
                .requires(Items.GLASS_BOTTLE)
                .requires(Items.RED_DYE)
                .unlockedBy(getHasName(SlotsItems.SHRIMP.get()), has(SlotsItems.SHRIMP.get()))
                .save(p_251297_);

    }
}
