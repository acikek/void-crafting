package com.acikek.voidcrafting.advancement;

import com.acikek.voidcrafting.VoidCrafting;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class VoidCraftSuccessCriterion extends AbstractCriterion<VoidCraftSuccessCriterion.Conditions> {

    public static Identifier ID = VoidCrafting.id("void_craft_success");

    @Override
    protected Conditions conditionsFromJson(JsonObject obj, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        JsonPrimitive recipe = obj.getAsJsonPrimitive("recipe");
        JsonPrimitive count = obj.getAsJsonPrimitive("count");
        return new Conditions(
                playerPredicate,
                recipe != null ? Identifier.tryParse(recipe.getAsString()) : null,
                count != null ? count.getAsInt() : 0
        );
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    public void trigger(ServerPlayerEntity player, Identifier recipe, int size) {
        trigger(player, conditions -> conditions.matches(recipe, size));
    }

    public static class Conditions extends AbstractCriterionConditions {

        public Identifier recipe;
        public int size;

        public Conditions(EntityPredicate.Extended playerPredicate, Identifier recipe, int size) {
            super(ID, playerPredicate);
            this.recipe = recipe;
            this.size = size;
        }

        public boolean matches(Identifier recipe, int size) {
            return this.recipe.equals(recipe) && size >= this.size;
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject obj = super.toJson(predicateSerializer);
            obj.add("recipe", new JsonPrimitive(recipe.toString()));
            obj.add("size", new JsonPrimitive(size));
            return obj;
        }
    }
}
