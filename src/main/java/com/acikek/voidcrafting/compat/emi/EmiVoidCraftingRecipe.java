package com.acikek.voidcrafting.compat.emi;

import com.acikek.voidcrafting.recipe.VoidRecipe;
import dev.emi.emi.EmiRenderHelper;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class EmiVoidCraftingRecipe implements EmiRecipe {

    public Identifier id;
    public EmiIngredient input;
    public EmiStack output;
    public float radius;
    public String offset;
    public String world;
    public boolean replicate;

    public List<TooltipComponent> tooltip;

    public String getCoordinate(float value, boolean absolute) {
        return (absolute ? "~" : "") + value;
    }

    public String getOffset(float x, float z, boolean absolute) {
        return getCoordinate(x, absolute) + ", " + getCoordinate(z, absolute);
    }

    public EmiVoidCraftingRecipe(VoidRecipe recipe) {
        id = recipe.id();
        input = EmiIngredient.of(recipe.input());
        output = recipe.getOutput() != null ? EmiStack.of(recipe.getOutput()) : null;
        radius = recipe.position().radius();
        offset = getOffset(recipe.position().x(), recipe.position().z(), recipe.position().absolute());
        world = recipe.position().worldKey().getValue().toString();
        replicate = recipe.replicate();
        tooltip = List.of(
                TooltipComponent.of(new TranslatableText("emi.voidcrafting.offset", offset, radius).asOrderedText()),
                TooltipComponent.of(new TranslatableText("emi.voidcrafting.world", world).asOrderedText())
        );
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return VoidCraftingPlugin.VOID_CRAFTING;
    }

    @Override
    public @Nullable Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return Collections.singletonList(input);
    }

    @Override
    public List<EmiStack> getOutputs() {
        return output == null ? input.getEmiStacks() : Collections.singletonList(output);
    }

    @Override
    public int getDisplayWidth() {
        return 90;
    }

    @Override
    public int getDisplayHeight() {
        return 54;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        int centerX = widgets.getWidth() / 2;
        int centerY = widgets.getHeight() / 2;
        widgets.addTexture(VoidCraftingPlugin.WIDGETS, centerX - 9, centerY - 27, 54, 54, 0, 0);
        widgets.addTexture(
                replicate ? VoidCraftingPlugin.WIDGETS : EmiRenderHelper.WIDGETS,
                centerX - 22,
                centerY - 9,
                24, 17,
                replicate ? 54 : 44, 0
        ).tooltip(() -> tooltip);
        widgets.addSlot(input, 2, centerY - 9);
        if (!replicate) {
            widgets.addSlot(output, centerX + 17, centerY - 9).output(true).recipeContext(this).drawBack(false);
        }
    }
}
