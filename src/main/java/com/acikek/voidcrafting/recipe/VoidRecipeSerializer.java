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
        JsonObject result;
        String world;
        float x;
        float z;
        float radius;
        boolean absolute;
    }

    public static final VoidRecipeSerializer INSTANCE = new VoidRecipeSerializer();

    @Override
    public VoidRecipe read(Identifier id, JsonObject json) {
        JsonFormat recipeJson = new Gson().fromJson(json, JsonFormat.class);
        if (recipeJson.input == null) {
            throw new JsonSyntaxException("Missing field 'input'");
        }
        if (recipeJson.result == null) {
            throw new JsonSyntaxException("Missing field 'result'");
        }
        RegistryKey<World> worldKey = recipeJson.world != null
                ? RegistryKey.of(Registry.WORLD_KEY, Identifier.tryParse(recipeJson.world))
                : DEFAULT_WORLD_KEY;
        Ingredient input = Ingredient.fromJson(recipeJson.input);
        ItemStack result = ShapedRecipe.outputFromJson(recipeJson.result);
        float radius = recipeJson.radius == 0.0f ? DEFAULT_RADIUS : recipeJson.radius;
        return new VoidRecipe(input, result, worldKey, recipeJson.x, recipeJson.z, radius, recipeJson.absolute, id);
    }

    @Override
    public VoidRecipe read(Identifier id, PacketByteBuf buf) {
        Ingredient input = Ingredient.fromPacket(buf);
        ItemStack result = buf.readItemStack();
        RegistryKey<World> worldKey = RegistryKey.of(Registry.WORLD_KEY, Identifier.tryParse(buf.readString()));
        float x = buf.readFloat();
        float z = buf.readFloat();
        float radius = buf.readFloat();
        boolean absolute = buf.readBoolean();
        return new VoidRecipe(input, result, worldKey, x, z, radius, absolute, id);
    }

    @Override
    public void write(PacketByteBuf buf, VoidRecipe recipe) {
        recipe.input().write(buf);
        buf.writeItemStack(recipe.result());
        buf.writeString(recipe.worldKey().getValue().toString());
        buf.writeFloat(recipe.x());
        buf.writeFloat(recipe.z());
        buf.writeFloat(recipe.radius());
        buf.writeBoolean(recipe.absolute());
    }
}
