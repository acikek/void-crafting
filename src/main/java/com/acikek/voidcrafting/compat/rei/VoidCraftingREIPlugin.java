package com.acikek.voidcrafting.compat.rei;

import com.acikek.voidcrafting.recipe.VoidRecipe;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;

public class VoidCraftingREIPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new VoidRecipeCategory());
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerFiller(VoidRecipe.class, VoidRecipeDisplay::new);
    }
}
