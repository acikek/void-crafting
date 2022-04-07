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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;

import java.util.Random;

public record VoidRecipe(Ingredient input, float x, float z, float radius,
                         RegistryKey<World> worldKey, boolean absolute,
                         boolean replicate, ItemStack result, Identifier id) implements Recipe<SimpleInventory> {

    public static final Identifier ID = VoidCrafting.id("void_crafting");

    public static Vec2f getPosition(float radius, Random random) {
        float angle = random.nextFloat(MathHelper.TAU);
        float cos = MathHelper.cos(angle);
        float sin = MathHelper.sin(angle);
        return new Vec2f(radius * cos, radius * -sin);
    }

    public boolean isValid() {
        return replicate || !result.isEmpty();
    }

    public World getWorld(World world) {
        if (world.getServer() == null) {
            return null;
        }
        return world.getServer().getWorld(worldKey);
    }

    public ItemStack getStack(ItemEntity itemEntity) {
        ItemStack stack = (replicate ? itemEntity.getStack() : result).copy();
        if (replicate) {
            stack.setCount(1);
        }
        return stack;
    }

    public void dropItems(ItemEntity itemEntity, World world) {
        for (int i = 0; i < itemEntity.getStack().getCount(); i++) {
            Vec2f pos = getPosition(radius, world.random);
            float posX = pos.x + x + (absolute ? (float) itemEntity.getX() : 0.0f);
            float posZ = pos.y + z + (absolute ? (float) itemEntity.getZ() : 0.0f);
            int y = world.getTopY(Heightmap.Type.WORLD_SURFACE, (int) posX, (int) posZ);
            ItemEntity drop = new ItemEntity(world, posX, y, posZ, getStack(itemEntity));
            world.spawnEntity(drop);
        }
    }

    public void triggerCriterion(ItemEntity itemEntity, World world) {
        if (itemEntity.getThrower() != null) {
            PlayerEntity player = world.getPlayerByUuid(itemEntity.getThrower());
            if (player != null) {
                ModCriteria.VOID_CRAFT_SUCCESS.trigger((ServerPlayerEntity) player, id, itemEntity.getStack().getCount());
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
