package com.acikek.voidcrafting.recipe;

import com.acikek.voidcrafting.VoidCrafting;
import com.acikek.voidcrafting.advancement.ModCriteria;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public record VoidRecipe(Ingredient input, Position position,
                         boolean replicate, ItemStack result, Identifier id) implements Recipe<SimpleInventory> {

    public static final Identifier ID = VoidCrafting.id("void_crafting");

    public boolean isValid() {
        return replicate || !result.isEmpty();
    }

    public void triggerCriterion(ItemEntity itemEntity, ServerPlayerEntity thrower) {
        ModCriteria.VOID_CRAFT_SUCCESS.trigger(thrower, id, itemEntity.getStack().getCount());
    }

    public void activate(World world, ItemEntity itemEntity, PlayerEntity thrower) {
        if (isValid()) {
            World target = position.dropItems(world, itemEntity, replicate, result, id);
            if (target != null && thrower instanceof ServerPlayerEntity serverPlayer) {
                triggerCriterion(itemEntity, serverPlayer);
            }
        }
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        return inventory.size() >= 1 && input.test(inventory.getStack(0));
    }

    @Override
    public ItemStack craft(SimpleInventory inventory) {
        return null;
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getOutput() {
        return result;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return VoidRecipeSerializer.INSTANCE;
    }

    public static class Type implements RecipeType<VoidRecipe> {
        public static final Type INSTANCE = new Type();
    }

    public static void register() {
        Registry.register(Registry.RECIPE_TYPE, ID, Type.INSTANCE);
        Registry.register(Registry.RECIPE_SERIALIZER, ID, VoidRecipeSerializer.INSTANCE);
    }
}
