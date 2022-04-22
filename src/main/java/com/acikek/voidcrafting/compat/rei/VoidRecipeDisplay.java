package com.acikek.voidcrafting.compat.rei;

import com.acikek.voidcrafting.VoidCrafting;
import com.acikek.voidcrafting.recipe.VoidRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.Collections;
import java.util.List;

public class VoidRecipeDisplay implements Display {

    public static final CategoryIdentifier<VoidRecipeDisplay> IDENTIFIER = CategoryIdentifier.of(VoidCrafting.id("voidcrafting"));

    /*public float radius;
    public float x;
    public float y;*/
    public List<EntryIngredient> input;
    public List<EntryIngredient> output;
    public boolean absolute;

    public VoidRecipeDisplay(VoidRecipe recipe) {
        input = Collections.singletonList(EntryIngredients.ofIngredient(recipe.input())); //CollectionUtils.map(recipe.input().getMatchingStacks(), EntryIngredients::of);
        output = Collections.singletonList(EntryIngredients.of(recipe.getOutput()));
        absolute = recipe.absolute();
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return input;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return output;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return IDENTIFIER;
    }
}
