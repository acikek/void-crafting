package com.acikek.voidcrafting.compat.rei;

import com.acikek.voidcrafting.VoidCrafting;
import com.acikek.voidcrafting.recipe.VoidRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.CollectionUtils;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VoidRecipeDisplay implements Display {

    public static final CategoryIdentifier<VoidRecipeDisplay> IDENTIFIER = CategoryIdentifier.of(VoidCrafting.id("voidcrafting"));

    /*public float radius;
    public float x;
    public float y;*/
    public List<EntryIngredient> inputs;
    public List<EntryIngredient> outputs;
    public boolean absolute;

    public VoidRecipeDisplay(VoidRecipe recipe) {
        inputs = Collections.singletonList(EntryIngredients.of(recipe.input().getMatchingStacks()[0])); //CollectionUtils.map(recipe.input().getMatchingStacks(), EntryIngredients::of);
        outputs = Collections.singletonList(EntryIngredients.of(recipe.getOutput()));
        absolute = recipe.absolute();
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return inputs;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return outputs;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return IDENTIFIER;
    }

    /*public static BasicDisplay.Serializer<VoidRecipeDisplay> getSerializer() {
        return BasicDisplay.Serializer.ofRecipeLess(VoidRecipeDisplay::new, (display, nbt) -> {
            nbt.putBoolean("Absolute", display.absolute);
        });
    }*/
}
