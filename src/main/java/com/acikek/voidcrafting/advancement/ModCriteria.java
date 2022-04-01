package com.acikek.voidcrafting.advancement;

import net.fabricmc.fabric.api.object.builder.v1.advancement.CriterionRegistry;

public class ModCriteria {

    public static VoidCraftSuccessCriterion VOID_CRAFT_SUCCESS = new VoidCraftSuccessCriterion();

    public static void register() {
        CriterionRegistry.register(VOID_CRAFT_SUCCESS);
    }
}
