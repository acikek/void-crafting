package com.acikek.voidcrafting.compat.emi;

import com.acikek.voidcrafting.VoidCrafting;
import com.acikek.voidcrafting.recipe.VoidRecipe;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiRenderable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.util.Identifier;

public class VoidCraftingPlugin implements EmiPlugin {

    public static final Identifier WIDGETS = VoidCrafting.id("textures/gui/widgets.png");

    public static final EmiRenderable CATEGORY_RENDERER = (matrices, x, y, delta) -> {
        RenderSystem.setShaderTexture(0, WIDGETS);
        DrawableHelper.drawTexture(matrices, x, y, 78, 0, 16, 16, 256, 256);
    };

    public static EmiRecipeCategory VOID_CRAFTING = new EmiRecipeCategory(
            VoidCrafting.id("void_crafting"),
            CATEGORY_RENDERER, CATEGORY_RENDERER
    );

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(VOID_CRAFTING);
        for (VoidRecipe recipe : registry.getRecipeManager().listAllOfType(VoidRecipe.Type.INSTANCE)) {
            registry.addRecipe(new EmiVoidCraftingRecipe(recipe));
        }
    }
}
