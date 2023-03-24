
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

    public List<EntryIngredient> input;
    public List<EntryIngredient> output;
    public float radius;
    public String offset;
    public String world;

    public String getCoordinate(float value, boolean absolute) {
        return (absolute ? "~" : "") + value;
    }

    public String getOffset(float x, float z, boolean absolute) {
        return getCoordinate(x, absolute) + ", " + getCoordinate(z, absolute);
    }

    public VoidRecipeDisplay(VoidRecipe recipe) {
        EntryIngredient inputIngredient = EntryIngredients.ofIngredient(recipe.input());
        input = Collections.singletonList(inputIngredient);
        output = Collections.singletonList(recipe.replicate() ? inputIngredient : EntryIngredients.of(recipe.getOutput(null)));
        radius = recipe.position().radius();
        offset = getOffset(recipe.position().x(), recipe.position().z(), recipe.position().absolute());
        world = recipe.position().worldKey().getValue().toString();
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
