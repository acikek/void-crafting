package com.acikek.voidcrafting.api;

import com.acikek.voidcrafting.recipe.Position;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

@SuppressWarnings("unused")
public class VoidCraftingAPI {

    public static String VOID_RESISTANCE_KEY = "VoidResistance";
    public static MutableText TOOLTIP_LINE = Text.translatable("tooltip.voidcrafting.void_resistance");

    private VoidCraftingAPI() {
    }

    /**
     * Adds void resistance to a stack based on the provided {@link Position}.<br>
     * Encodes the position data onto the stack using {@link Position#toNbt()} under the {@link VoidCraftingAPI#VOID_RESISTANCE_KEY} key.
     * @return The modified stack.
     */
    public static ItemStack setVoidResistance(ItemStack stack, Position position) {
        stack.getOrCreateNbt().put(VOID_RESISTANCE_KEY, position.toNbt());
        return stack;
    }

    /**
     * Returns whether a stack is void-resistant.<br>
     * Only checks if the stack's NBT contains {@link VoidCraftingAPI#VOID_RESISTANCE_KEY}, regardless of its contents.
     */
    public static boolean isVoidResistant(ItemStack stack) {
        return stack.hasNbt() && stack.getOrCreateNbt().contains(VOID_RESISTANCE_KEY);
    }

    /**
     * Returns the {@link Position} instance encoded into a stack's NBT.<br>
     * This should only be called on a void-resistant stack. Default values may be inferred for missing or invalid values.
     * @see VoidCraftingAPI#setVoidResistance(ItemStack, Position)
     * @see VoidCraftingAPI#isVoidResistant(ItemStack)
     * @see Position#fromNbt(NbtCompound)
     */
    public static Position getVoidResistancePosition(ItemStack stack) {
        return Position.fromNbt(stack.getOrCreateNbt().getCompound(VOID_RESISTANCE_KEY));
    }

    /**
     * Removes void resistance data from a stack. Does nothing if {@link ItemStack#hasNbt()} returns {@code false}.
     */
    public static void removeVoidResistance(ItemStack stack) {
        if (stack.hasNbt()) {
            stack.getOrCreateNbt().remove(VOID_RESISTANCE_KEY);
        }
    }

    /**
     * If a stack is void-resistant, appends a "Void-Resistant" line to the second position of the tooltip.
     * If {@link TooltipContext#isAdvanced()} returns true, also appends the positional information based on {@link Position#toString()}.<br>
     * Can be used as a method reference for {@link net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback}.
     * @see VoidCraftingAPI#isVoidResistant(ItemStack)
     */
    public static void appendVoidResistanceTooltip(ItemStack stack, TooltipContext context, List<Text> lines) {
        if (isVoidResistant(stack)) {
            lines.add(1, TOOLTIP_LINE.copy().formatted(Formatting.DARK_PURPLE));
            if (context.isAdvanced()) {
                String position = VoidCraftingAPI.getVoidResistancePosition(stack).toString();
                lines.add(2, Text.literal(position).formatted(Formatting.DARK_GRAY));
            }
        }
    }
}
