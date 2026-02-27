package com.birdthyme.slotstime.datagen;

import com.birdthyme.slotstime.blocks.SlotsBlocks;
import com.birdthyme.slotstime.items.SlotsItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ForgeItemTagsProvider;

import java.util.function.Consumer;

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider {
    public RecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> p_251297_) {

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, SlotsItems.SHRIMP.get(), 8)
                .requires(Items.COD)
                .requires(Items.PINK_DYE)
                .unlockedBy(getHasName(Items.COD), has(Items.COD))
                .save(p_251297_, "slotstime:cod_shrimp");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, SlotsItems.SHRIMP.get(), 8)
                .requires(Items.SALMON)
                .requires(Items.PINK_DYE)
                .unlockedBy(getHasName(Items.SALMON), has(Items.SALMON))
                .save(p_251297_, "slotstime:salmon_shrimp");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, SlotsItems.SHRIMP_COCKTAIL.get())
                .requires(SlotsItems.SHRIMP.get())
                .requires(SlotsItems.SHRIMP.get())
                .requires(SlotsItems.SHRIMP.get())
                .requires(SlotsItems.SHRIMP.get())
                .requires(Items.GLASS_BOTTLE)
                .requires(Items.RED_DYE)
                .unlockedBy(getHasName(SlotsItems.SHRIMP.get()), has(SlotsItems.SHRIMP.get()))
                .save(p_251297_);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SlotsItems.COINMOLD.get(), 2)
                .pattern("NBN")
                .pattern("III")
                .define('N', Items.IRON_NUGGET)
                .define('B', Items.STONE_BUTTON)
                .define('I', Items.IRON_INGOT)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(p_251297_);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SlotsItems.IRONCOIN.get(), 4)
                .pattern("III")
                .pattern("IMI")
                .pattern("III")
                .define('M', SlotsItems.COINMOLD.get())
                .define('I', Items.IRON_INGOT)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(p_251297_);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SlotsItems.GOLDCOIN.get(), 4)
                .pattern("III")
                .pattern("IMI")
                .pattern("III")
                .define('M', SlotsItems.COINMOLD.get())
                .define('I', Items.GOLD_INGOT)
                .unlockedBy(getHasName(Items.GOLD_INGOT), has(Items.GOLD_INGOT))
                .save(p_251297_);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SlotsItems.DIAMONDCOIN.get(), 4)
                .pattern("III")
                .pattern("IMI")
                .pattern("III")
                .define('M', SlotsItems.COINMOLD.get())
                .define('I', Items.DIAMOND)
                .unlockedBy(getHasName(Items.DIAMOND), has(Items.DIAMOND))
                .save(p_251297_);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SlotsItems.NETHERITECOIN.get(), 4)
                .pattern("III")
                .pattern("IMI")
                .pattern("III")
                .define('M', SlotsItems.COINMOLD.get())
                .define('I', Items.NETHERITE_SCRAP)
                .unlockedBy(getHasName(Items.NETHERITE_SCRAP), has(Items.NETHERITE_SCRAP))
                .save(p_251297_, "slotstime:scrap_netherite_coin");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SlotsItems.NETHERITECOIN.get(), 4)
                .requires(Items.NETHERITE_INGOT)
                .requires(Items.NETHERITE_INGOT)
                .requires(SlotsItems.COINMOLD.get())
                .unlockedBy(getHasName(Items.NETHERITE_INGOT), has(Items.NETHERITE_INGOT))
                .save(p_251297_, "slotstime:ingot_netherite_coin");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SlotsItems.CCCCCCCCOIN.get(), 2)
                .pattern("ISI")
                .pattern("SMS")
                .pattern("ISI")
                .define('M', SlotsItems.COINMOLD.get())
                .define('I', Items.NETHERITE_INGOT)
                .define('S', Items.NETHER_STAR)
                .unlockedBy(getHasName(Items.NETHER_STAR), has(Items.NETHER_STAR))
                .save(p_251297_);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SlotsBlocks.SLOTMACHINE_ITEM.get(), 1)
                .pattern(" R ")
                .pattern("RHL")
                .pattern("BDB")
                .define('H', Items.HOPPER)
                .define('D', Items.DROPPER)
                .define('B', Items.BLACK_CONCRETE)
                .define('R', Items.RED_CONCRETE)
                .define('L', Items.LEVER)
                .unlockedBy(getHasName(Items.HOPPER), has(Items.HOPPER))
                .save(p_251297_);

    }
}
