package com.acikek.voidcrafting.compat.rei;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Label;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.ArrayList;
import java.util.List;

public class VoidRecipeCategory implements DisplayCategory<VoidRecipeDisplay> {

    public static final MutableText TITLE = new TranslatableText("rei.voidcrafting.void_recipe");
    public static final EntryStack<ItemStack> ICON = EntryStacks.of(Blocks.END_PORTAL_FRAME);

    public static final int TEXT_LIGHT = 0xFF404040;
    public static final int TEXT_DARK = 0xFFBBBBBB;

    @Override
    public Renderer getIcon() {
        return ICON;
    }

    @Override
    public Text getTitle() {
        return TITLE;
    }

    @Override
    public CategoryIdentifier<? extends VoidRecipeDisplay> getCategoryIdentifier() {
        return VoidRecipeDisplay.IDENTIFIER;
    }

    public Label applyFormat(Label label) {
        return label.noShadow().leftAligned().color(TEXT_LIGHT, TEXT_DARK);
    }

    @Override
    public List<Widget> setupDisplay(VoidRecipeDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 64, bounds.getCenterY() - 30);
        Point outputPoint = new Point(startPoint.x + 84, startPoint.y + 8);
        Point textPoint = new Point(bounds.x + 10, bounds.getCenterY() + 16);
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 50, startPoint.y + 7)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 27, startPoint.y + 8))
                .entries(display.getInputEntries().get(0)).markInput());
        widgets.add(Widgets.createResultSlotBackground(outputPoint));
        widgets.add(Widgets.createSlot(outputPoint)
                .entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        widgets.add(applyFormat(Widgets.createLabel(new Point(textPoint.x, textPoint.y - 12),
                new TranslatableText("rei.voidcrafting.offset", display.offset, display.radius))));
        widgets.add(applyFormat(Widgets.createLabel(textPoint,
                new TranslatableText("rei.voidcrafting.world", display.world))));
        return widgets;
    }
}
