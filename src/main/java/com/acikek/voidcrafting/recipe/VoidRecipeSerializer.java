package com.acikek.voidcrafting.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class VoidRecipeSerializer implements RecipeSerializer<VoidRecipe> {

    public static final float DEFAULT_RADIUS = 5.0f;
    public static final RegistryKey<World> DEFAULT_WORLD_KEY = World.END;

    public static class JsonFormat {
        JsonObject input;
        float x;
        float z;
        float radius;
        String world;
        boolean absolute;
        boolean replicate;
        JsonObject result;
    }

    public static final VoidRecipeSerializer INSTANCE = new VoidRecipeSerializer();

    @Override
    public VoidRecipe read(Identifier id, JsonObject json) {
        JsonFormat recipeJson = new Gson().fromJson(json, JsonFormat.class);
        if (recipeJson.input == null) {
            throw new JsonSyntaxException("Missing field 'input'");
        }
        if (recipeJson.result == null && !recipeJson.replicate) {
            throw new JsonSyntaxException("Missing field 'result'");
        }
        Ingredient input = Ingredient.fromJson(recipeJson.input);
        RegistryKey<World> worldKey = recipeJson.world != null
                ? RegistryKey.of(Registry.WORLD_KEY, Identifier.tryParse(recipeJson.world))
                : DEFAULT_WORLD_KEY;
        ItemStack result = !recipeJson.replicate ? ShapedRecipe.outputFromJson(recipeJson.result) : null;
        float radius = recipeJson.radius == 0.0f ? DEFAULT_RADIUS : recipeJson.radius;
        return new VoidRecipe(input, recipeJson.x, recipeJson.z, radius, worldKey, recipeJson.absolute, recipeJson.replicate, result, id);
    }

    @Override
    public VoidRecipe read(Identifier id, PacketByteBuf buf) {
        Ingredient input = Ingredient.fromPacket(buf);
        float x = buf.readFloat();
        float z = buf.readFloat();
        float radius = buf.readFloat();
        RegistryKey<World> worldKey = RegistryKey.of(Registry.WORLD_KEY, Identifier.tryParse(buf.readString()));
        boolean absolute = buf.readBoolean();
        boolean replicate = buf.readBoolean();
        ItemStack result = !replicate ? buf.readItemStack() : null;
        return new VoidRecipe(input, x, z, radius, worldKey, absolute, replicate, result, id);
    }

    @Override
    public void write(PacketByteBuf buf, VoidRecipe recipe) {
        recipe.input().write(buf);
        buf.writeFloat(recipe.x());
        buf.writeFloat(recipe.z());
        buf.writeFloat(recipe.radius());
        buf.writeString(recipe.worldKey().getValue().toString());
        buf.writeBoolean(recipe.absolute());
        buf.writeBoolean(recipe.replicate());
        if (recipe.result() != null) {
            buf.writeItemStack(recipe.result());
        }
    }
}
