package com.acikek.voidcrafting.api;

import com.acikek.voidcrafting.recipe.Position;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.util.List;

@SuppressWarnings("unused")
public class VoidCraftingAPI {

    public static String VOID_RESISTANCE_KEY = "VoidResistance";
    public static MutableText TOOLTIP_LINE = new TranslatableText("tooltip.voidcrafting.void_resistance");

    public static ItemStack setVoidResistance(ItemStack stack, Position position) {
        stack.getOrCreateNbt().put(VOID_RESISTANCE_KEY, position.toNbt());
        return stack;
    }

    public static boolean isVoidResistant(ItemStack stack) {
        return stack.hasNbt() && stack.getOrCreateNbt().contains(VOID_RESISTANCE_KEY);
    }

    public static Position getVoidResistancePosition(ItemStack stack) {
        return Position.fromNbt(stack.getOrCreateNbt().getCompound(VOID_RESISTANCE_KEY));
    }

    public static void removeVoidResistance(ItemStack stack) {
        if (stack.hasNbt()) {
            stack.getOrCreateNbt().remove(VOID_RESISTANCE_KEY);
        }
    }

    public static void appendVoidResistanceTooltip(ItemStack stack, TooltipContext context, List<Text> lines) {
        if (isVoidResistant(stack)) {
            int pos = lines.size() <= 1 ? lines.size() - 1 : 1;
            lines.add(pos, TOOLTIP_LINE.copy().formatted(Formatting.DARK_PURPLE));
            if (context.isAdvanced()) {
                String position = VoidCraftingAPI.getVoidResistancePosition(stack).toString();
                lines.add(pos + 1, new LiteralText(position).formatted(Formatting.DARK_GRAY));
            }
        }
    }
}
