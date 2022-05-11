package com.acikek.voidcrafting.recipe;

import com.acikek.voidcrafting.api.PositionBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.JsonOps;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.recipe.api.serializer.QuiltRecipeSerializer;

public class VoidRecipeSerializer implements QuiltRecipeSerializer<VoidRecipe> {

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
        PositionBuilder builder = new PositionBuilder()
                .coordinates(recipeJson.x, recipeJson.z)
                .absolute(recipeJson.absolute);
        if (recipeJson.world != null) {
            builder = builder.worldKey(recipeJson.world);
        }
        if (recipeJson.radius > 0.0f) {
            builder = builder.radius(recipeJson.radius);
        }
        ItemStack result = !recipeJson.replicate ? ShapedRecipe.outputFromJson(recipeJson.result) : null;
        return new VoidRecipe(input, builder.build(), recipeJson.replicate, result, id);
    }

    @Override
    public JsonObject toJson(VoidRecipe recipe) {
        JsonObject obj = new JsonObject();
        obj.add("input", recipe.input().toJson());
        recipe.position().writeJson(obj);
        obj.add("replicate", new JsonPrimitive(recipe.replicate()));
        if (!recipe.replicate()) {
            ItemStack.CODEC.encode(recipe.result(), JsonOps.INSTANCE, JsonOps.INSTANCE.empty())
                    .result()
                    .ifPresent(jsonElement -> obj.add("result", jsonElement));
        }
        return obj;
    }

    @Override
    public VoidRecipe read(Identifier id, PacketByteBuf buf) {
        Ingredient input = Ingredient.fromPacket(buf);
        Position position = Position.read(buf);
        boolean replicate = buf.readBoolean();
        ItemStack result = !replicate ? buf.readItemStack() : null;
        return new VoidRecipe(input, position, replicate, result, id);
    }

    @Override
    public void write(PacketByteBuf buf, VoidRecipe recipe) {
        recipe.input().write(buf);
        recipe.position().write(buf);
        buf.writeBoolean(recipe.replicate());
        if (recipe.result() != null) {
            buf.writeItemStack(recipe.result());
        }
    }
}
